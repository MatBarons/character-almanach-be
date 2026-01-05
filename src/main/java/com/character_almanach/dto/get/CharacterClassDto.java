package com.character_almanach.dto.get;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CharacterClassDto {
    private String className;
    private String subclassName;
    private int level;
}
