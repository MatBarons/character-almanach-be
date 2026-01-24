package com.character_almanach.dto.get.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserLoginDto {
    private String username;
    private String password;
}
