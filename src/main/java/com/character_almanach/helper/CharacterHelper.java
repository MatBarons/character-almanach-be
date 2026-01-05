package com.character_almanach.helper;

import com.character_almanach.dto.get.CharacterClassDto;
import com.character_almanach.dto.get.CharacterDto;
import com.character_almanach.dto.put.CharacterUpdateDto;

public class CharacterHelper {
    public static boolean checkSubClassValidity(CharacterUpdateDto characterUpdateDto, CharacterDto existingCharacter) {
        for(int i = 0; i < characterUpdateDto.getClasses().size(); i++) {
            CharacterClassDto updatedClass = characterUpdateDto.getClasses().get(i);
            CharacterClassDto existingClass = existingCharacter.getClasses().get(i);
            if(updatedClass.getSubclassName() == null && existingClass.getSubclassName() != null) {
                return false;
            }
            if(updatedClass.getSubclassName() != null && 
                existingClass.getSubclassName() != null && 
                !updatedClass.getSubclassName().equals(existingClass.getSubclassName())) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkClassRemoval(CharacterUpdateDto characterUpdateDto, CharacterDto existingCharacter) {
        for (CharacterClassDto existingClass : existingCharacter.getClasses()) {
            boolean classExistsInUpdate = characterUpdateDto.getClasses().stream()
                .anyMatch(updatedClass -> updatedClass.getClassName().equals(existingClass.getClassName()));
            if (!classExistsInUpdate) {
                return false; // Class removal detected
            }
        }
        return true; // No class removal
    }
}
