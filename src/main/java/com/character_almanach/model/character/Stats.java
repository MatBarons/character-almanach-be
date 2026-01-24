package com.character_almanach.model.character;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Stats {

    @Column(nullable = false)
    private int strength;

    @Column(nullable = false)
    private int dexterity;

    @Column(nullable = false)
    private int constitution;

    @Column(nullable = false)
    private int intelligence;

    @Column(nullable = false)
    private int wisdom;

    @Column(nullable = false)
    private int charisma;
}
