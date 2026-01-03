package com.character_almanach.service.interfaces;

import java.util.List;

import com.character_almanach.dto.get.CharacterDto;

public interface ICharacterService {
    List<CharacterDto> getAllCharacters();
    CharacterDto getCharacter(Long id);
}
