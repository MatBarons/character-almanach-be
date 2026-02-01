package com.character_almanach.dto.get.user;

import java.util.List;

import com.character_almanach.model.user.Roles;
import com.character_almanach.dto.get.character.CharacterDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private Roles role;
    private List<CharacterDto> characters;
}
