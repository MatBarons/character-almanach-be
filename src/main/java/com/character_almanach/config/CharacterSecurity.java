package com.character_almanach.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.character_almanach.exception.character.CharacterNotFoundException;
import com.character_almanach.model.character.GameCharacter;
import com.character_almanach.repository.CharacterRepository;

@Component
public class CharacterSecurity {

    @Autowired
    private CharacterRepository characterRepository;

    public boolean isOwner(Long characterId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        GameCharacter character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException(characterId));
        return character.getUser().getUsername().equals(username);
    }
}

