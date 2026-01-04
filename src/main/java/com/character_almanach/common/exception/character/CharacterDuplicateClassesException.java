package com.character_almanach.common.exception.character;

public class CharacterDuplicateClassesException extends RuntimeException {
    public CharacterDuplicateClassesException() {
        super("Character cannot have duplicate classes.");
    }
    
}
