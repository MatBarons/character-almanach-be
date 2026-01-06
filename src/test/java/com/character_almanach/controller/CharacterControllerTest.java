package com.character_almanach.controller;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.CharacterClassDto;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.dto.get.StatsDto;
import com.character_almanach.mappers.CharacterMapper;
import com.character_almanach.model.Character;
import com.character_almanach.model.CharacterClass;
import com.character_almanach.model.Stats;
import com.character_almanach.service.CharacterService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CharacterController.class)
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

        mockMvc.perform(get("/characters/all"))
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
            .andExpect(jsonPath("$.totalLevel").value(5))
            .andExpect(jsonPath("$.race").value("Human"));
    }

    @Test
    void shouldSaveCharacter() throws Exception {

        CharacterCreateDto character = new CharacterCreateDto(
            "Gandalf",
            20,
            "Human",
            new StatsDto(18, 14, 16, 20, 18, 17),
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(
            new CharacterDto(
                4L,
                "Gandalf",
                20,
                "Human",
                List.of(
                    new CharacterClassDto("Wizard", "School of Evocation", 20)
                ),
                new StatsDto(18, 14, 16, 20, 18, 17)
            )
        );

        mockMvc.perform(post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(character)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Gandalf"));
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterName() throws Exception {

        final CharacterCreateDto characterWithEmptyName = new CharacterCreateDto(
            "",
            20,
            "Elf",
            new StatsDto(10, 10, 10, 10, 10, 10),
            List.of(
                new CharacterClassDto("Rogue", "Thief", 20)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithEmptyName)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterLevel() throws Exception {

        final CharacterCreateDto characterWithInvalidLevel = new CharacterCreateDto(
            "Legolas",
            0,
            "Elf",
            new StatsDto(10, 10, 10, 10, 10, 10),
            List.of(
                new CharacterClassDto("Rogue", "Thief", 0)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithInvalidLevel)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterRace() throws Exception {

        final CharacterCreateDto characterWithEmptyRace = new CharacterCreateDto(
            "Legolas",
            20,
            "",
            new StatsDto(10, 10, 10, 10, 10, 10),
            List.of(
                new CharacterClassDto("Rogue", "Thief", 20)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithEmptyRace)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterLevelSum() throws Exception {

        final CharacterCreateDto characterWithInvalidLevelSum = new CharacterCreateDto(
            "Legolas",
            20,
            "Elf",
            new StatsDto(10, 10, 10, 10, 10, 10),
            List.of(
                new CharacterClassDto("Rogue", "Thief", 10),
                new CharacterClassDto("Archer", "Sniper", 15)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithInvalidLevelSum)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterMultipleSameClass() throws Exception {

        final CharacterCreateDto characterWithMultipleSameClass = new CharacterCreateDto(
            "Legolas",
            20,
            "Elf",
            new StatsDto(10, 10, 10, 10, 10, 10),
            List.of(
                new CharacterClassDto("Rogue", "Thief", 10),
                new CharacterClassDto("Rogue", "Assassin", 10)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithMultipleSameClass)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidStats() throws Exception {

        final CharacterCreateDto characterWithInvalidStats = new CharacterCreateDto(
            "Legolas",
            20,
            "Elf",
            new StatsDto(0, 10, 10, 10, 10, 10),
            List.of(
                new CharacterClassDto("Rogue", "Thief", 20)
            )
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithInvalidStats)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForNoCharacterClasses() throws Exception {

        final CharacterCreateDto characterWithNoClasses = new CharacterCreateDto(
            "Legolas",
            20,
            "Elf",
            new StatsDto(10, 10, 10, 10, 10, 10),
            List.of()
        );

        when(characterService.createCharacter(any(CharacterCreateDto.class))).thenReturn(new CharacterDto(
            4L,
            "Gandalf",
            20,
            "Human",
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 20)
            ),
            new StatsDto(18, 14, 16, 20, 18, 17)
        ));

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(characterWithNoClasses)))
        ).andExpect(status().isBadRequest());
    }

}
