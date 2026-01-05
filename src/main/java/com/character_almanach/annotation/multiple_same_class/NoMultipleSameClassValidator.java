package com.character_almanach.annotation.multiple_same_class;

import com.character_almanach.common.character.IHasClassesAndTotalLevel;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoMultipleSameClassValidator
        implements ConstraintValidator<ValidCharacterClasses, IHasClassesAndTotalLevel> {

    @Override
    public boolean isValid(IHasClassesAndTotalLevel character, ConstraintValidatorContext context) {
        if (character == null || character.getClasses() == null) {
            return true; // let @NotNull handle nulls
        }

        return character.getClasses().stream().map(c -> c.getClassName()).toList().size() != character.getClasses().stream().map(c -> c.getClassName()).distinct().toList().size();
    }
}

