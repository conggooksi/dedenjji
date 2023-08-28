package com.secondwind.dedenjji.api.club.clubMember.domain.entity;

import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.common.entity.BaseEntity;
import com.secondwind.dedenjji.common.enumerate.Level;
import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubMember extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_member_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated(EnumType.STRING)
    private Level level;
    @Enumerated(EnumType.STRING)
    private ClubAuthority clubAuthority;
    private boolean isAllowed;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public ClubMember(Member member, Club club, Level level, ClubAuthority clubAuthority, boolean isAllowed) {
        this.member = member;
        this.club = club;
        this.level = level;
        this.clubAuthority = clubAuthority;
        this.isAllowed = isAllowed;
    }

    public void makeIsAllowed() {
        this.isAllowed = true;
    }

    public void changeClubMemberLevel(Level level) {
        this.level = level;
    }
}
