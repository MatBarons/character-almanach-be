package com.character_almanach.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.character_almanach.common.exception.character.CharacterNotFoundException;
import com.character_almanach.common.exception.character.ReducingCharacterLevelException;
import com.character_almanach.common.exception.character.SubclassChangeNotAllowedException;
import com.character_almanach.common.mappers.CharacterMapper;
import com.character_almanach.common.utils.CharacterUtils;
import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;
import com.character_almanach.repository.CharacterRepository;
import com.character_almanach.service.interfaces.ICharacterService;

import jakarta.validation.Valid;

@Service
@Transactional
public class CharacterService implements ICharacterService{
    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public List<CharacterDto> getAllCharacters(){
        return this.characterRepository.findAll().stream().map(CharacterMapper::toDto).toList();
    }

    @Override
    public CharacterDto getCharacter(Long id){
        return CharacterMapper.toDto(this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException(id)));
    }
    
    @Override
    public CharacterDto createCharacter(@Valid CharacterCreateDto characterCreateDto) {
        return CharacterMapper.toDto(this.characterRepository.save(CharacterMapper.toEntity(characterCreateDto)));
    }

    @Override
    public CharacterDto updateCharacter(Long id, @Valid CharacterUpdateDto characterUpdateDto) {
        final CharacterDto existingCharacter = CharacterMapper.toDto(this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException(id)));
        if(existingCharacter.getTotalLevel() < characterUpdateDto.getTotalLevel()) {
            throw new ReducingCharacterLevelException(id);
        }
        if(!CharacterUtils.checkSubClassValidity(characterUpdateDto, existingCharacter)) {
            throw new SubclassChangeNotAllowedException(id);
        }
        return CharacterMapper.toDto(this.characterRepository.save(CharacterMapper.toEntity(characterUpdateDto)));
    }
}