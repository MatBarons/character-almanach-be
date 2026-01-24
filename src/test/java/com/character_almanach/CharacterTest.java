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
import com.character_almanach.dto.get.character.CharacterClassDto;
import com.character_almanach.dto.get.character.StatsDto;
import com.character_almanach.dto.put.CharacterUpdateDto;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


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

    @Test
    void shouldUpdateCharacter() throws Exception {
       CharacterUpdateDto character = new CharacterUpdateDto(
            19,
            new StatsDto(19, 19, 19, 19, 19, 19),
            List.of(
                new CharacterClassDto("Wizard", "School of Evocation", 19)
            )
        );

        mockMvc.perform(
            (put("/characters/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content((new ObjectMapper()).writeValueAsString(character)))
        ).andExpect(status().isAccepted());

        mockMvc.perform(get("/characters/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.stats.strength").value(19))
            .andExpect(jsonPath("$.stats.dexterity").value(19))
            .andExpect(jsonPath("$.stats.constitution").value(19))
            .andExpect(jsonPath("$.stats.intelligence").value(19))
            .andExpect(jsonPath("$.stats.wisdom").value(19))
            .andExpect(jsonPath("$.stats.charisma").value(19));
    }
    
}