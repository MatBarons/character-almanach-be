package com.character_almanach.mappers;

import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.model.user.User;

public final class UserMapper {
    public static UserDto toDto(User user){
        return new UserDto(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.getRole(),
            user.getCharacters().stream().map(CharacterMapper::toDto).toList()
        );
    }

    public static User toEntity(UserDto user){
        return new User(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            null,
            user.getRole(),
            user.getCharacters().stream().map(CharacterMapper::toEntity).toList()
        );
    }
}
