package com.character_almanach.controller;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureWebMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

import com.character_almanach.common.mappers.CharacterMapper;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.model.Character;
import com.character_almanach.model.CharacterClass;
import com.character_almanach.model.Stats;
import com.character_almanach.service.CharacterService;

@SpringBootTest
@AutoConfigureWebMvc
@Transactional
public class CharacterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CharacterService characterService;

    @Test
    void shouldReturnAllCharacters() throws Exception {
        Stats stats1 = new Stats(16, 14, 15, 12, 10, 13);
        Character character1 = new Character("Arthas", 5, "Human", stats1);
        character1.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character1.addClass(new CharacterClass("Warrior", "Samurai", 2));

        Stats stats2 = new Stats(10, 18, 12, 14, 13, 8);
        Character character2 = new Character("Lyra", 4, "Elf", stats2);
        character2.addClass(new CharacterClass("Rogue", "Thief", 4));
        Stats stats3 = new Stats(8, 12, 10, 18, 16, 14);
        Character character3 = new Character("Merlin", 7, "Human", stats3);
        character3.addClass(new CharacterClass("Wizard", "School of Evocation", 7));

        List<CharacterDto> mockCharacters = List.of(
            CharacterMapper.toDto(character1),
            CharacterMapper.toDto(character2),
            CharacterMapper.toDto(character3)
        );

        when(characterService.getAllCharacters()).thenReturn(mockCharacters);

        mockMvc.perform(get("/characters"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].name").value("Arthas"))
            .andExpect(jsonPath("$[1].name").value("Lyra"))
            .andExpect(jsonPath("$[2].name").value("Merlin"));
    }

    @Test
    void shouldReturnCharacterById() throws Exception {
        Stats stats1 = new Stats(16, 14, 15, 12, 10, 13);
        Character character1 = new Character("Arthas", 5, "Human", stats1);
        character1.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character1.addClass(new CharacterClass("Warrior", "Samurai", 2));

        when(characterService.getCharacter(1L)).thenReturn(CharacterMapper.toDto(character1));

        mockMvc.perform(get("/characters/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Arthas"))
            .andExpect(jsonPath("$.level").value(5))
            .andExpect(jsonPath("$.race").value("Human"));
    }

    void shouldReturnMissingCharacterById() throws Exception {
        when(characterService.getCharacter(999L)).thenReturn(null);

        mockMvc.perform(get("/characters/999"))
            .andExpect(status().isNotFound());
    }
}
