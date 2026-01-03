package com.character_almanach.dto.put;

import java.util.List;

import com.character_almanach.dto.get.CharacterClassDto;
import com.character_almanach.dto.get.StatsDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CharacterUpdateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String race;

    @Min(1)
    @Max(20)
    private int totalLevel;

    @Valid
    @NotNull
    private StatsDto stats;

    @Valid
    @NotEmpty
    private List<CharacterClassDto> classes;

    // getters & setters
}

