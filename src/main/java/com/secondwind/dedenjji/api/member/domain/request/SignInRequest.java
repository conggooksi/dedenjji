package com.secondwind.dedenjji.api.member.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class SignInRequest {
    private String loginId;
    private String password;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public SignInRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }
}
