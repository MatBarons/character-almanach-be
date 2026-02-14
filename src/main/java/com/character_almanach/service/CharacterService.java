package com.character_almanach.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.character.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;
import com.character_almanach.exception.character.CharacterNotFoundException;
import com.character_almanach.exception.character.ClassRemovalNotAllowedException;
import com.character_almanach.exception.character.ReducingCharacterLevelException;
import com.character_almanach.exception.character.SubclassChangeNotAllowedException;
import com.character_almanach.helper.CharacterHelper;
import com.character_almanach.mappers.CharacterClassMapper;
import com.character_almanach.mappers.CharacterMapper;
import com.character_almanach.mappers.StatsMapper;
import com.character_almanach.model.character.GameCharacter;
import com.character_almanach.model.user.User;
import com.character_almanach.model.character.CharacterClass;
import com.character_almanach.repository.CharacterRepository;
import com.character_almanach.repository.UserRepository;
import com.character_almanach.service.interfaces.ICharacterService;

@Service
@Transactional
public class CharacterService implements ICharacterService{
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CharacterDto> getAllCharacters(){
        return this.characterRepository.findAll().stream().map(CharacterMapper::toDto).toList();
    }

    @Override
    public List<CharacterDto> getAllCharactersByUserId(Long userId){
        return this.characterRepository.findByUserId(userId).stream().map(CharacterMapper::toDto).toList();
    }

    @Override
    @PreAuthorize("@characterSecurity.isOwner(#userId)")
    public CharacterDto getCharacter(Long id, Long userId){
        return CharacterMapper.toDto(this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException(id)));
    }
    
    @Override
    public CharacterDto createCharacter(@Valid CharacterCreateDto characterCreateDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        GameCharacter character = CharacterMapper.toEntity(characterCreateDto);
        character.setUser(user);
        return CharacterMapper.toDto(this.characterRepository.save(character));
    }

    @Override
    @PreAuthorize("@characterSecurity.isOwner(#userId)")
    public CharacterDto updateCharacter(Long id, @Valid CharacterUpdateDto characterUpdateDto, Long userId) {
        final GameCharacter existingCharacter = this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException(id));
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
    @PreAuthorize("@characterSecurity.isOwner(#userId)")
    public void deleteCharacter(Long id, Long userId) {
        this.characterRepository.deleteById(id);
    }
}