package com.secondwind.dedenjji.api.club.domain.entity;

import com.secondwind.dedenjji.api.club.clubMember.domain.entity.ClubMember;
import com.secondwind.dedenjji.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Club extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @Builder(builderMethodName = "of", builderClassName = "of")
    public Club(Long id, String name, List<ClubMember> clubMembers) {
        this.name = name;
    }

    @Builder(builderMethodName = "updateClubBuilder", builderClassName = "updateClubBuilder")
    public void updateClub(String name) {
        this.name = name;
    }
}
