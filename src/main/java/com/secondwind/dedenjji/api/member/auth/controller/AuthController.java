package com.secondwind.dedenjji.api.member.auth.controller;

import com.secondwind.dedenjji.api.member.auth.service.AuthService;
import com.secondwind.dedenjji.api.member.domain.dto.MemberJoinDTO;
import com.secondwind.dedenjji.common.exception.code.MemberErrorCode;
import com.secondwind.dedenjji.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag(name = "auth", description = "Auth API")
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
            @RequestBody MemberJoinDTO memberJoinDTO) {
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
}
