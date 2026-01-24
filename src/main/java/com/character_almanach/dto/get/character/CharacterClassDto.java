package com.character_almanach.dto.get.character;

import com.character_almanach.common.character.GenericClasses;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CharacterClassDto extends GenericClasses {
    private String className;
    private String subclassName;
    private int level;
}
