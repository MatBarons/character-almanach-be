package com.character_almanach.service.interfaces;

import java.util.List;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.character.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;

public interface ICharacterService {
    List<CharacterDto> getAllCharacters();
    CharacterDto getCharacter(Long id, Long userId);
    List<CharacterDto> getAllCharactersByUserId(Long userId);
    CharacterDto createCharacter(CharacterCreateDto characterCreateDto);
    CharacterDto updateCharacter(Long id, CharacterUpdateDto characterCreateDto, Long userId);
    void deleteCharacter(Long id, Long userId);
}
