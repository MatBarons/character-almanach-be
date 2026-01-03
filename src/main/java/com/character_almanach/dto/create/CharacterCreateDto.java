package com.character_almanach.dto.create;


import com.character_almanach.dto.get.CharacterClassDto;
import com.character_almanach.dto.get.StatsDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CharacterCreateDto {

    @NotBlank
    @NotNull
    private String name;

    @Min(1)
    @Max(20)
    private int totalLevel;

    @NotBlank
    @NotNull
    private String race;
    
    @Valid
    @NotNull
    private StatsDto stats;

    @Valid
    @NotNull
    private List<CharacterClassDto> classes;

}
