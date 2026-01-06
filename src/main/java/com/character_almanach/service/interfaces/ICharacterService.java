package com.character_almanach.service.interfaces;

import java.util.List;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;

public interface ICharacterService {
    List<CharacterDto> getAllCharacters();
    CharacterDto getCharacter(Long id);
    CharacterDto createCharacter(CharacterCreateDto characterCreateDto);
    CharacterDto updateCharacter(Long id, CharacterUpdateDto characterCreateDto);
    void deleteCharacter(Long id);
}
