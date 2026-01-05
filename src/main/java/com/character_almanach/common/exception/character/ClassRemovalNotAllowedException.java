package com.character_almanach.common.exception.character;

public class ClassRemovalNotAllowedException extends RuntimeException {
    public ClassRemovalNotAllowedException(Long characterId) {
        super("Class removal is not allowed for character with ID: " + characterId);
    }
    
}
