package com.character_almanach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.character_almanach.dto.create.CharacterCreateDto;
import com.character_almanach.dto.get.CharacterClassDto;
import com.character_almanach.dto.get.StatsDto;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class CharacterTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnAllCharacters() throws Exception {
        mockMvc.perform(get("/characters/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].name").value("Arthas"))
            .andExpect(jsonPath("$[1].name").value("Lyra"))
            .andExpect(jsonPath("$[2].name").value("Merlin"));
    }

    @Test
    void shouldReturnCharacterById() throws Exception {
        mockMvc.perform(get("/characters/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Arthas"));
    }

    @Test
    void shouldReturnCharacterNotFoundForInvalidId() throws Exception {
        mockMvc.perform(get("/characters/999"))
            .andExpect(status().isNotFound());
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

        mockMvc.perform(
            (post("/characters/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(character)))
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/characters/4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Gandalf"));
    }

    //TESTS FOR VALIDATIONS
    /* 
    @Test
    void shouldReturnValidationErrorForInvalidCharacterName() throws Exception {
        mockMvc.perform(
            post("/characters/new",
                new CharacterCreateDto(
                    "",
                    20,
                    "Elf",
                    new StatsDto(10, 10, 10, 10, 10, 10),
                    List.of(
                        new CharacterClassDto("Rogue", "Thief", 20)
                    )
                )
            )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterLevel() throws Exception {
        mockMvc.perform(
            post("/characters/new",
                new CharacterCreateDto(
                    "Legolas",
                    25,
                    "Elf",
                    new StatsDto(10, 10, 10, 10, 10, 10),
                    List.of(
                        new CharacterClassDto("Rogue", "Thief", 25)
                    )
                )
            )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterRace() throws Exception {
        mockMvc.perform(
            post("/characters/new",
                new CharacterCreateDto(
                    "Legolas",
                    20,
                    "",
                    new StatsDto(10, 10, 10, 10, 10, 10),
                    List.of(
                        new CharacterClassDto("Rogue", "Thief", 20)
                    )
                )
            )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterLevelSum() throws Exception {
        mockMvc.perform(
            post("/characters/new",
                new CharacterCreateDto(
                    "Legolas",
                    20,
                    "Elf",
                    new StatsDto(10, 10, 10, 10, 10, 10),
                    List.of(
                        new CharacterClassDto("Rogue", "Thief", 15),
                        new CharacterClassDto("Paladin", "Oath of the Ancients", 15)
                    )
                )
            )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorForInvalidCharacterMultipleSameClass() throws Exception {
        mockMvc.perform(
            post("/characters/new",
                new CharacterCreateDto(
                    "Legolas",
                    20,
                    "Elf",
                    new StatsDto(10, 10, 10, 10, 10, 10),
                    List.of(
                        new CharacterClassDto("Rogue", "Thief", 10),
                        new CharacterClassDto("Rogue", "Assassin", 10)
                    )
                )
            )
        ).andExpect(status().isBadRequest());
    }
    */
}