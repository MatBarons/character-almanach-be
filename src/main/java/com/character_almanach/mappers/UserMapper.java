package com.character_almanach.mappers;

import java.util.ArrayList;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.model.user.Roles;
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

    public static User toEntity(UserRegisterDto user){
        return new User(
            null,
            user.getEmail(),
            user.getUsername(),
            user.getPassword(),
            Roles.USER,
            new ArrayList<>()
        );
    }
}
