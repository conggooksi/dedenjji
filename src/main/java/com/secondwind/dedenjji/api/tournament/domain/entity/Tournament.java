package com.secondwind.dedenjji.api.tournament.domain.entity;

import com.secondwind.dedenjji.api.category.domain.entity.Category;
import com.secondwind.dedenjji.api.tournamentMember.domain.entity.TournamentMember;
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
public class Tournament extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<TournamentMember> tournamentMembers = new ArrayList<>();

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Tournament(Long id, String name, Category category, List<TournamentMember> tournamentMembers) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.tournamentMembers = tournamentMembers;
    }

    public void updateTournament(String name) {
        this.name = name;
    }

    public void deleteTournament() {
        this.isDeleted = true;
    }
}
