package com.character_almanach.dto.get;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StatsDto {
    @Min(1)
    @Max(30)
    @NotNull
    private int strength;

    @Min(1)
    @Max(30)
    @NotNull
    private int dexterity;

    @Min(1)
    @Max(30)
    @NotNull
    private int constitution;

    @Min(1)
    @Max(30)
    @NotNull
    private int intelligence;
    
    @Min(1)
    @Max(30)
    @NotNull
    private int wisdom;
    
    @Min(1)
    @Max(30)
    @NotNull
    private int charisma;
}
