package com.character_almanach.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.character.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;
import com.character_almanach.model.user.CustomUserDetails;
import com.character_almanach.service.CharacterService;
import com.character_almanach.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<CharacterDto> getAllCharacters(){
        return this.characterService.getAllCharacters();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<CharacterDto> getAllCharactersByUserId(){
        return this.characterService.getAllCharactersByUserId(userService.getUserId());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CharacterDto getCharacter(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails){
        return this.characterService.getCharacter(id, userDetails.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public CharacterDto createCharacter(@RequestBody @Valid CharacterCreateDto entity) {
        return this.characterService.createCharacter(entity);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    public CharacterDto updateCharacter(@PathVariable Long id, @RequestBody @Valid CharacterUpdateDto entity, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return this.characterService.updateCharacter(id, entity, userDetails.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        this.characterService.deleteCharacter(id, userDetails.getId());
    }
}
