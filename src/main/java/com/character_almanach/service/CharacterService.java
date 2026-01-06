package com.character_almanach.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;
import com.character_almanach.exception.character.CharacterNotFoundException;
import com.character_almanach.exception.character.ClassRemovalNotAllowedException;
import com.character_almanach.exception.character.ReducingCharacterLevelException;
import com.character_almanach.exception.character.SubclassChangeNotAllowedException;
import com.character_almanach.helper.CharacterHelper;
import com.character_almanach.mappers.CharacterClassMapper;
import com.character_almanach.mappers.CharacterMapper;
import com.character_almanach.mappers.StatsMapper;
import com.character_almanach.repository.CharacterRepository;
import com.character_almanach.service.interfaces.ICharacterService;
import com.character_almanach.model.Character;
import com.character_almanach.model.CharacterClass;

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
        final Character existingCharacter = this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException(id));

        final CharacterDto existingCharacterDto = CharacterMapper.toDto(existingCharacter);

        if(characterUpdateDto.getTotalLevel() < existingCharacterDto.getTotalLevel()) {
            throw new ReducingCharacterLevelException(id);
        }

        if(!CharacterHelper.checkClassRemoval(characterUpdateDto, existingCharacterDto)) {
            throw new ClassRemovalNotAllowedException(id);
        }

        if(!CharacterHelper.checkSubClassValidity(characterUpdateDto, existingCharacterDto)) {
            throw new SubclassChangeNotAllowedException(id);
        }

        //DOING THE FOLLOWING CAUSE OF JPA -- IT EXPECT THE ENTITY TO BE MODIFIED DIRECTLY, NOT REPLACED
        
        //UPDATING ONE TO MANY RELATIONSHIP
        final List<CharacterClass> updatedClasses = new ArrayList<>(
        characterUpdateDto.getClasses()
            .stream()
            .map(CharacterClassMapper::toEntity)
            .toList()
        );
        updatedClasses.forEach(existingCharacter::addClass);

        //UPDATING CHARACTER FIELDS
        existingCharacter.setStats(StatsMapper.toEntity(characterUpdateDto.getStats()));
        existingCharacter.setTotalLevel(characterUpdateDto.getTotalLevel());

        return CharacterMapper.toDto(this.characterRepository.save(existingCharacter));
    }

    @Override
    public void deleteCharacter(Long id) {
        this.characterRepository.deleteById(id);
    }
}