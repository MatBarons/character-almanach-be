package com.character_almanach.dto.get;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CharacterDto {
    private Long id;
    private String name;
    private int totalLevel;
    private String race;
    private List<CharacterClassDto> classes;
    private StatsDto stats;
}
