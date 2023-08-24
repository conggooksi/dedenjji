package com.secondwind.dedenjji.api.member.auth.service;

import com.secondwind.dedenjji.api.member.auth.domain.request.TokenRequestDTO;
import com.secondwind.dedenjji.api.member.domain.request.SignInRequest;
import com.secondwind.dedenjji.api.member.domain.request.SignUpRequest;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.member.domain.spec.PasswordSpecification;
import com.secondwind.dedenjji.api.member.repository.MemberRepository;
import com.secondwind.dedenjji.common.dto.TokenDTO;
import com.secondwind.dedenjji.common.exception.CustomAuthException;
import com.secondwind.dedenjji.common.exception.code.AuthErrorCode;
import com.secondwind.dedenjji.common.provider.JwtTokenProvider;
import com.secondwind.dedenjji.common.result.JsonResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final PasswordSpecification passwordSpecification;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long signup(SignUpRequest memberJoinDTO) {
        passwordSpecification.check(memberJoinDTO.getPassword());

        if (memberRepository.existsByLoginIdAndIsDeletedFalse(memberJoinDTO.getLoginId())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.ALREADY_JOIN_USER.getCode())
                    .errorMessage(AuthErrorCode.ALREADY_JOIN_USER.getMessage())
                    .build());
        }

        if (memberRepository.existsByNicknameAndIsDeletedFalse(memberJoinDTO.getNickname())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.ALREADY_EXISTS_NICKNAME.getCode())
                    .errorMessage(AuthErrorCode.ALREADY_EXISTS_NICKNAME.getMessage())
                    .build());
        }

        Member member = memberJoinDTO.toMember(memberJoinDTO, passwordEncoder);

        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional
    public TokenDTO login(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return getTokenDTO(authentication);
    }

    @Override
    @Transactional
    public TokenDTO reissue(TokenRequestDTO tokenRequestDTO) {
        if (!jwtTokenProvider.validateToken(tokenRequestDTO.getRefreshToken())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.NOT_VALID_TOKEN.getCode())
                    .errorMessage(AuthErrorCode.NOT_VALID_TOKEN.getMessage())
                    .build());
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());
        String refreshToken = redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if (refreshToken == null || !refreshToken.equals(tokenRequestDTO.getRefreshToken())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.NOT_MATCH_TOKEN_INFO.getCode())
                    .errorMessage(AuthErrorCode.NOT_MATCH_TOKEN_INFO.getMessage())
                    .build());
        }

        return getTokenDTO(authentication);
    }

    @Override
    public void logout(TokenRequestDTO tokenRequestDTO) {
        if (!jwtTokenProvider.validateToken(tokenRequestDTO.getAccessToken())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.NOT_VALID_TOKEN.getCode())
                    .errorMessage(AuthErrorCode.NOT_VALID_TOKEN.getMessage())
                    .build());
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }

        Long expiration = jwtTokenProvider.getExpiration(tokenRequestDTO.getAccessToken());
        redisTemplate.opsForValue()
                .set(tokenRequestDTO.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

    }

    private TokenDTO getTokenDTO(Authentication authentication) {
        TokenDTO tokenDTO = jwtTokenProvider.generateTokenDTO(authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(),
                        tokenDTO.getRefreshToken(),
                        tokenDTO.getRefreshTokenExpiresIn(),
                        TimeUnit.MILLISECONDS);

        Optional<Member> optionalMember = memberRepository.findById(Long.valueOf(authentication.getName()));
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.changeLastAccessToken(tokenDTO.getAccessToken());
        }

        return tokenDTO;
    }
}
