package com.secondwind.dedenjji.api.category.domain.entity;

import com.secondwind.dedenjji.api.tournament.domain.entity.Tournament;
import com.secondwind.dedenjji.common.entity.BaseEntity;
import jakarta.persistence.*;
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
}
