package com.character_almanach.mappers;

import com.character_almanach.dto.get.character.StatsDto;
import com.character_almanach.model.character.Stats;

public final class StatsMapper {

    public static StatsDto toDto(Stats stats) {
        return new StatsDto(
            stats.getStrength(),
            stats.getDexterity(),
            stats.getConstitution(),
            stats.getIntelligence(),
            stats.getWisdom(),
            stats.getCharisma()
        );
    }

    public static Stats toEntity(StatsDto dto) {
        return new Stats(
            dto.getStrength(),
            dto.getDexterity(),
            dto.getConstitution(),
            dto.getIntelligence(),
            dto.getWisdom(),
            dto.getCharisma()
        );
    }
}
