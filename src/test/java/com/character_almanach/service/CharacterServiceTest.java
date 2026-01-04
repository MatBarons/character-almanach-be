package com.character_almanach.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.character_almanach.model.CharacterClass;
import com.character_almanach.model.Stats;
import com.character_almanach.common.mappers.CharacterMapper;
import com.character_almanach.model.Character;
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
        Character character1 = new Character("Arthas", 5, "Human", stats1);
        character1.addClass(new CharacterClass("Paladin", 3));
        character1.addClass(new CharacterClass("Warrior", 2));

        Stats stats2 = new Stats(10, 18, 12, 14, 13, 8);
        Character character2 = new Character("Lyra", 4, "Elf", stats2);
        character2.addClass(new CharacterClass("Rogue", 4));

        Stats stats3 = new Stats(8, 12, 10, 18, 16, 14);
        Character character3 = new Character("Merlin", 7, "Human", stats3);
        character3.addClass(new CharacterClass("Wizard", 7));

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
        Character character = new Character("Arthas", 5, "Human", stats);
        character.addClass(new CharacterClass("Paladin", 3));
        character.addClass(new CharacterClass("Warrior", 2));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        assertEquals(characterService.getCharacter(1L), CharacterMapper.toDto(character));
    }
}
