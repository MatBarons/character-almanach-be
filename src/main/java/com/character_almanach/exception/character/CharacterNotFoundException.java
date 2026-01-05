package com.character_almanach.exception.character;

public class CharacterNotFoundException extends RuntimeException{

    public CharacterNotFoundException(Long id){
        super("Missing character exception for ID: " + id);
    }
}
