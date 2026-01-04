package com.character_almanach.model;

import java.util.ArrayList;
import java.util.List;

import com.character_almanach.common.annotation.total_level.ValidTotalLevel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "characters")
@ValidTotalLevel
@NoArgsConstructor
@Getter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int totalLevel;

    @Column(nullable = false, length = 30)
    private String race;

    @Embedded
    private Stats stats;

    @OneToMany(
        mappedBy = "character",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<CharacterClass> classes = new ArrayList<>();

    public Character(String name, int totalLevel, String race, Stats stats) {
        this.name = name;
        this.totalLevel = totalLevel;
        this.race = race;
        this.stats = stats;
    }

    public Character(int totalLevel, Stats stats) {
        this.totalLevel = totalLevel;
        this.stats = stats;
    }

    public void addClass(CharacterClass c) {
        classes.add(c);
        c.setCharacter(this);
    }

    public void removeClass(CharacterClass c) {
        classes.remove(c);
        c.setCharacter(null);
    }
}
