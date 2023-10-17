package com.secondwind.dedenjji.api.category.domain.entity;

import com.secondwind.dedenjji.api.tournament.domain.entity.Tournament;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Tournament> tournaments = new ArrayList<>();

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Category(Long id, String name, List<Tournament> tournaments) {
        this.id = id;
        this.name = name;
        this.tournaments = tournaments;
    }

    @Builder(builderClassName = "updateCategoryBuilder", builderMethodName = "updateCategoryBuilder")
    public void updateCategory(String name) {
        this.name = name;
    }
}
