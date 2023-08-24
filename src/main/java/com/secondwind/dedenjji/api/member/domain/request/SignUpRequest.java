package com.secondwind.dedenjji.api.member.domain.request;

import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.common.enumerate.Authority;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignUpRequest {
    private String loginId;
    private String password;
    private String nickname;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public SignUpRequest(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }

    public Member toMember(SignUpRequest memberJoinDTO, PasswordEncoder passwordEncoder) {
        return Member.of()
                .loginId(memberJoinDTO.getLoginId())
                .password(passwordEncoder.encode(memberJoinDTO.getPassword()))
                .nickname(memberJoinDTO.getNickname())
                .authority(Authority.ROLE_USER)
                .build();
    }
}
