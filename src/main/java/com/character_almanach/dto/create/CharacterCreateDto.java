package com.character_almanach.dto.create;


import com.character_almanach.annotation.multiple_same_class.ValidCharacterClasses;
import com.character_almanach.annotation.total_level.ValidTotalLevel;
import com.character_almanach.common.character.GenericCharacter;
import com.character_almanach.dto.get.character.CharacterClassDto;
import com.character_almanach.dto.get.character.StatsDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Setter
@AllArgsConstructor
@ValidTotalLevel
@ValidCharacterClasses
public class CharacterCreateDto extends GenericCharacter<CharacterClassDto> {

    @Getter
    @NotBlank
    @NotNull
    private String name;

    @Min(1)
    @Max(20)
    private int totalLevel;

    @Getter
    @NotBlank
    @NotNull
    private String race;
    
    @Getter
    @Valid
    @NotNull
    private StatsDto stats;

    @Valid
    @NotNull
    private List<CharacterClassDto> classes;

    @Override
    public List<CharacterClassDto> getClasses() {
        return classes;
    }

    @Override
    public int getTotalLevel() {
        return totalLevel;
    }

}
