package com.character_almanach.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.character_almanach.dto.get.character.CharacterClassDto;
import com.character_almanach.dto.get.character.StatsDto;
import com.character_almanach.dto.put.CharacterUpdateDto;
import com.character_almanach.exception.character.CharacterNotFoundException;
import com.character_almanach.exception.character.ClassRemovalNotAllowedException;
import com.character_almanach.exception.character.ReducingCharacterLevelException;
import com.character_almanach.exception.character.SubclassChangeNotAllowedException;
import com.character_almanach.mappers.CharacterMapper;
import com.character_almanach.model.character.GameCharacter;
import com.character_almanach.model.character.CharacterClass;
import com.character_almanach.model.character.Stats;
import com.character_almanach.repository.CharacterRepository;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {
    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterService characterService;



    @Test
    void shouldReturnAllCharacters() {
        Stats stats1 = new Stats(16, 14, 15, 12, 10, 13);
        GameCharacter character1 = new GameCharacter("Arthas", 5, "Human", stats1);
        character1.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character1.addClass(new CharacterClass("Warrior", "Samurai", 2));

        Stats stats2 = new Stats(10, 18, 12, 14, 13, 8);
        GameCharacter character2 = new GameCharacter("Lyra", 4, "Elf", stats2);
        character2.addClass(new CharacterClass("Rogue", "Thief", 4));

        Stats stats3 = new Stats(8, 12, 10, 18, 16, 14);
        GameCharacter character3 = new GameCharacter("Merlin", 7, "Human", stats3);
        character3.addClass(new CharacterClass("Wizard", "School of Evocation", 7));
        when(characterRepository.findAll()).thenReturn(List.of(character1, character2, character3));

        assertArrayEquals(characterService.getAllCharacters().toArray(), List.of(
            CharacterMapper.toDto(character1),
            CharacterMapper.toDto(character2),
            CharacterMapper.toDto(character3)
        ).toArray());
    }

    @Test
    void shouldReturnCharacterById() {
        Stats stats = new Stats(16, 14, 15, 12, 10, 13);
        GameCharacter character = new GameCharacter("Arthas", 5, "Human", stats);
        character.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character.addClass(new CharacterClass("Warrior", "Samurai", 2));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        assertEquals(characterService.getCharacter(1L), CharacterMapper.toDto(character));
    }

    @Test
    void shouldThrowExceptionForMissingCharacterById() {
        when(characterRepository.findById(999L))
            .thenReturn(Optional.empty());

        assertThrows(
            CharacterNotFoundException.class,
            () -> characterService.getCharacter(999L)
        );
    }

    @Test
    void shouldThrowExceptionForLevelReduction() {

        Stats stats = new Stats(16, 14, 15, 12, 10, 13);
        GameCharacter character = new GameCharacter("Arthas", 5, "Human", stats);
        character.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character.addClass(new CharacterClass("Warrior", "Samurai", 2));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        final CharacterUpdateDto updateDto = new CharacterUpdateDto(
            2,
            new StatsDto(16, 14, 15, 12, 10, 13),
            List.of(
                new CharacterClassDto("Paladin", "Oath of the Ancients", 1),
                new CharacterClassDto("Warrior", "Battle Master", 1)
            )
        );
        
        assertThrows(
            ReducingCharacterLevelException.class
            , () -> characterService.updateCharacter(1L, updateDto)
        );
    }

    @Test
    void shouldThrowExceptionForClassRemoval() {

        Stats stats = new Stats(16, 14, 15, 12, 10, 13);
        GameCharacter character = new GameCharacter("Arthas", 5, "Human", stats);
        character.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character.addClass(new CharacterClass("Warrior", "Samurai", 2));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        
        final CharacterUpdateDto updateDto = new CharacterUpdateDto(
            5,
            new StatsDto(16, 14, 15, 12, 10, 13),
            List.of(
                new CharacterClassDto("Warrior", "Battle Master", 2)
            )
        );
        
        assertThrows(
            ClassRemovalNotAllowedException.class
            , () -> characterService.updateCharacter(1L, updateDto)
        );
    }

    @Test
    void shouldThrowExceptionForSubclassChange() {

        Stats stats = new Stats(16, 14, 15, 12, 10, 13);
        GameCharacter character = new GameCharacter("Arthas", 5, "Human", stats);
        character.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character.addClass(new CharacterClass("Warrior", "Samurai", 2));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        final CharacterUpdateDto updateDto = new CharacterUpdateDto(
            5,
            new StatsDto(16, 14, 15, 12, 10, 13),
            List.of(
                new CharacterClassDto("Paladin", "Oath of Conquest", 3),
                new CharacterClassDto("Warrior", "Battle Master", 2)
            )
        );
        
        assertThrows(
            SubclassChangeNotAllowedException.class, 
            () -> characterService.updateCharacter(1L, updateDto)
        );
    }
}
