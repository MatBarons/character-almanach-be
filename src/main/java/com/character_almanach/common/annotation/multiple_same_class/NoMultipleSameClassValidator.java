package com.character_almanach.common.annotation.multiple_same_class;

import com.character_almanach.model.Character;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoMultipleSameClassValidator
        implements ConstraintValidator<ValidCharacterClasses, Character> {

    @Override
    public boolean isValid(Character character, ConstraintValidatorContext context) {
        if (character == null || character.getClasses() == null) {
            return true; // let @NotNull handle nulls
        }

        return character.getClasses().stream().map(c -> c.getClassName()).toList().size() != character.getClasses().stream().map(c -> c.getClassName()).distinct().toList().size();
    }
}

