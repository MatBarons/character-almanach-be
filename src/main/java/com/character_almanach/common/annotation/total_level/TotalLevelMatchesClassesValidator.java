package com.character_almanach.common.annotation.total_level;

import com.character_almanach.model.CharacterClass;
import com.character_almanach.model.Character;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TotalLevelMatchesClassesValidator
        implements ConstraintValidator<ValidTotalLevel, Character> {

    @Override
    public boolean isValid(Character character, ConstraintValidatorContext context) {
        if (character == null || character.getClasses() == null) {
            return true; // let @NotNull handle nulls
        }

        int sum = character.getClasses()
                .stream()
                .mapToInt(CharacterClass::getLevel)
                .sum();

        return sum == character.getTotalLevel();
    }
}

