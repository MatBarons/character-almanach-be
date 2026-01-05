package com.character_almanach.exception.character;

public class ReducingCharacterLevelException extends RuntimeException {
    public ReducingCharacterLevelException(Long id) {
        super("Cannot reduce the level of character with id: " + id);
    }
    
}
