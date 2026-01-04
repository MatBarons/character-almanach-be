package com.character_almanach.common.exception.character;

public class SubclassChangeNotAllowedException extends RuntimeException {
    public SubclassChangeNotAllowedException(Long characterId) {
        super("Subclass change is not allowed for character with ID: " + characterId);
    }
    
}
