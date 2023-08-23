package com.secondwind.dedenjji.api.member.domain.entity;

import com.secondwind.dedenjji.api.teamMember.domain.entity.TeamMember;
import com.secondwind.dedenjji.common.entity.BaseEntity;
import com.secondwind.dedenjji.common.enumerate.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Where(clause = "is_deleted = false")
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TeamMember> teamMembers = new ArrayList<>();

    @Builder(builderMethodName = "of", builderClassName = "of")
    public Member(Long id, String loginId, String password, String nickname, Authority authority, boolean isDeleted) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
        this.isDeleted = isDeleted;
    }
}
