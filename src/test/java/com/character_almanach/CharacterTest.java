package com.character_almanach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CharacterTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllCharacters() throws Exception {
        mockMvc.perform(get("/characters"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void shouldReturnCharacterById() throws Exception {
        mockMvc.perform(get("/characters/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Arthas"));
    }
}
