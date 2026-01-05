package com.character_almanach.model;

import com.character_almanach.common.character.GenericClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classes")
@Getter
@NoArgsConstructor
public class CharacterClass extends GenericClasses{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 30)
    private String className;

    @Column(nullable = true, length = 30)
    private String subclassName;

    @Column(nullable = false)
    private int level;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    public CharacterClass(String className, String subclassName, int level) {
        this.className = className;
        this.subclassName = subclassName;
        this.level = level;
    }
}

