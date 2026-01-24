package com.character_almanach.dto.put;

import java.util.List;

import com.character_almanach.annotation.multiple_same_class.ValidCharacterClasses;
import com.character_almanach.annotation.total_level.ValidTotalLevel;
import com.character_almanach.common.character.GenericCharacter;
import com.character_almanach.dto.get.character.CharacterClassDto;
import com.character_almanach.dto.get.character.StatsDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@AllArgsConstructor
@ValidTotalLevel
@ValidCharacterClasses
public class CharacterUpdateDto extends GenericCharacter<CharacterClassDto> {

    @Min(1)
    @Max(20)
    private int totalLevel;

    @Getter
    @Valid
    @NotNull
    private StatsDto stats;

    @Valid
    @NotEmpty
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

