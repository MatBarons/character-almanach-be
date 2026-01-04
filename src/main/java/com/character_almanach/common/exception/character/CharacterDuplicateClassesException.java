package com.character_almanach.common.exception.character;

public class CharacterDuplicateClassesException extends RuntimeException {
    public CharacterDuplicateClassesException(String className) {
        super("Character cannot have duplicate classes. A class has been added more than once: " + className);
    }
    
}
