package com.character_almanach.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRegisterDto {
    @NotNull
    @NotBlank
    public String username;
    @NotNull
    @NotBlank
    public String email;
    @NotNull
    @NotBlank
    public String password;
}
