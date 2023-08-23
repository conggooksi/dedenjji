package com.secondwind.dedenjji.api.tournamentMember.domain.entity;

import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.tournament.domain.entity.Tournament;
import com.secondwind.dedenjji.common.entity.BaseEntity;
import com.secondwind.dedenjji.common.enumerate.WinLose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TournamentMember extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member opponent;

    @Enumerated(EnumType.STRING)
    private WinLose winLose;
    private boolean isDeleted;
}
