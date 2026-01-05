package com.character_almanach.mappers;
import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;
import com.character_almanach.model.Character;
import com.character_almanach.model.CharacterClass;


public final class CharacterMapper {


    public static CharacterDto toDto(Character entity) {
        return new CharacterDto(
            entity.getId(),
            entity.getName(),
            entity.getTotalLevel(),
            entity.getRace(),
            entity.getClasses().stream().map(CharacterClassMapper::toDto).toList(),
            StatsMapper.toDto(entity.getStats())
        );
    }

    //for the create - POST
    public static Character toEntity(CharacterCreateDto dto) {

        Character character = new Character(
            dto.getName(),
            dto.getTotalLevel(),
            dto.getRace(),
            StatsMapper.toEntity(dto.getStats())
        );

        dto.getClasses().forEach(classDto -> {
            CharacterClass clazz =
                CharacterClassMapper.toEntity(classDto);
            character.addClass(clazz); 
        });

        return character;
    }

    public static Character toEntity(Long id,CharacterUpdateDto dto) {

        Character character = new Character(
            dto.getTotalLevel(),
            StatsMapper.toEntity(dto.getStats())
        );

        dto.getClasses().forEach(classDto -> {
            CharacterClass clazz =
                CharacterClassMapper.toEntity(classDto);
            character.addClass(clazz); 
        });

        return character;
    }
}
