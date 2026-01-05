package com.character_almanach.annotation.total_level;

import com.character_almanach.common.character.GenericClasses;
import com.character_almanach.common.character.IHasClassesAndTotalLevel;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TotalLevelMatchesClassesValidator
        implements ConstraintValidator<ValidTotalLevel,IHasClassesAndTotalLevel> {

    @Override
    public boolean isValid(IHasClassesAndTotalLevel character, ConstraintValidatorContext context) {
        if (character == null || character.getClasses() == null) {
            return true; // let @NotNull handle nulls
        }

        int sum = character.getClasses()
                .stream()
                .mapToInt(GenericClasses::getLevel)
                .sum();

        return sum == character.getTotalLevel();
    }
}

