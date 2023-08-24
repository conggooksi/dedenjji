package com.secondwind.dedenjji.api.member.auth.controller;

import com.secondwind.dedenjji.api.member.auth.domain.request.TokenRequestDTO;
import com.secondwind.dedenjji.api.member.auth.service.AuthService;
import com.secondwind.dedenjji.api.member.domain.request.SignUpRequest;
import com.secondwind.dedenjji.api.member.domain.request.SignInRequest;
import com.secondwind.dedenjji.common.dto.TokenDTO;
import com.secondwind.dedenjji.common.exception.code.MemberErrorCode;
import com.secondwind.dedenjji.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag(name = "Auth", description = "회원가입, 로그인, 로그아웃, reissue API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final String BASIC_PREFIX = "Basic ";
    private final AuthService authService;

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody SignUpRequest memberJoinDTO) {
        if (authorization != null) {
            String authBasic = authorization.substring(BASIC_PREFIX.length());
            String decodedAuthBasic = new String(Base64.getDecoder().decode(authBasic), StandardCharsets.UTF_8);
            String[] authUserInfo = decodedAuthBasic.split(":");

            String loginId = authUserInfo[0];
            String password = authUserInfo[1];

            memberJoinDTO.setLoginId(loginId);
            memberJoinDTO.setPassword(password);

            Long id = authService.signup(memberJoinDTO);
            return ResponseHandler.generate()
                    .data(id)
                    .status(HttpStatus.CREATED)
                    .build();
        } else {
            return ResponseHandler.failResultGenerate()
                    .errorMessage(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getMessage())
                    .errorCode(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getCode())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {

        if (authorization != null && !authorization.isBlank()) {
            String authBasic = authorization.substring(BASIC_PREFIX.length());

            String decodeAuthBasic = new String(Base64.getDecoder().decode(authBasic), StandardCharsets.UTF_8);
            String[] authUserInfo = decodeAuthBasic.split(":");

            String loginId = authUserInfo[0];
            String password = authUserInfo[1];

            SignInRequest signInRequest = SignInRequest.of()
                    .loginId(loginId)
                    .password(password)
                    .build();

            TokenDTO tokenDTO = authService.login(signInRequest);

            return ResponseHandler.generate()
                    .data(tokenDTO)
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponseHandler.failResultGenerate()
                    .errorCode(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getCode())
                    .errorMessage(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Operation(summary = "reissue API")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Valid @RequestBody TokenRequestDTO tokenRequestDTO) {
        TokenDTO tokenDTO = authService.reissue(tokenRequestDTO);
        return ResponseHandler.generate()
                .data(tokenDTO)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody TokenRequestDTO tokenRequestDTO) {
        authService.logout(tokenRequestDTO);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }
}
