package com.character_almanach.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.character_almanach.annotation.total_level.ValidTotalLevel;
import com.character_almanach.common.character.GenericCharacter;
import com.character_almanach.exception.character.CharacterDuplicateClassesException;

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
public class Character extends GenericCharacter {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int totalLevel;

    @Getter
    @Column(nullable = false, length = 30)
    private String race;

    @Getter
    @Embedded
    private Stats stats;

    @OneToMany(
        mappedBy = "character",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CharacterClass> classes = new HashSet<>();

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
        final boolean res = classes.add(c);
        if(!res){
            throw new CharacterDuplicateClassesException(c.getClassName());
        }
        c.setCharacter(this);
    }

    @Override
    public List<CharacterClass> getClasses() {
        return classes.stream().toList();
    }
    @Override
    public int getTotalLevel() {
        return totalLevel;
    }
}
