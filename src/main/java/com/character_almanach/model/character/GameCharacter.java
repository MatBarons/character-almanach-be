package com.character_almanach.model.character;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.character_almanach.annotation.total_level.ValidTotalLevel;
import com.character_almanach.common.character.GenericCharacter;
import com.character_almanach.exception.character.CharacterDuplicateClassesException;
import com.character_almanach.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "characters")
@ValidTotalLevel
@NoArgsConstructor
@AllArgsConstructor
public class GameCharacter extends GenericCharacter<CharacterClass> {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, length = 50)
    private String name;

    @Setter
    @Column(nullable = false)
    private int totalLevel;

    @Getter
    @Column(nullable = false, length = 30)
    private String race;

    @Setter
    @Getter
    @Embedded
    private Stats stats;

    @Setter
    @OneToMany(
        mappedBy = "character",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CharacterClass> classes = new HashSet<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    

    public GameCharacter(String name, int totalLevel, String race, Stats stats) {
        this.name = name;
        this.totalLevel = totalLevel;
        this.race = race;
        this.stats = stats;
    }

    public GameCharacter(Long id,String name, int totalLevel, String race, Stats stats){
        this.id = id;
        this.name = name;
        this.totalLevel = totalLevel;
        this.race = race;
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
