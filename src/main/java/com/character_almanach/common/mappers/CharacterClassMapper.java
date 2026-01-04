package com.character_almanach.common.mappers;

import com.character_almanach.dto.get.CharacterClassDto;
import com.character_almanach.model.CharacterClass;

public final class CharacterClassMapper {

    public static CharacterClassDto toDto(CharacterClass entity) {
        return new CharacterClassDto(
            entity.getClassName(),
            entity.getSubclassName(),
            entity.getLevel()
        );
    }

    public static CharacterClass toEntity(CharacterClassDto dto) {
        return new CharacterClass(
            dto.getClassName(),
            dto.getSubclassName(),
            dto.getLevel()
        );
    }
}
