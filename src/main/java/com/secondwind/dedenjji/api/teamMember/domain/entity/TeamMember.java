package com.secondwind.dedenjji.api.teamMember.domain.entity;

import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.team.domain.entity.Team;
import com.secondwind.dedenjji.common.entity.BaseEntity;
import com.secondwind.dedenjji.common.enumerate.Level;
import com.secondwind.dedenjji.common.enumerate.TeamAuthority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TeamMember extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private Level level;
    @Enumerated(EnumType.STRING)
    private TeamAuthority teamAuthority;

}
