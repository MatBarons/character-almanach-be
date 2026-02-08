package com.character_almanach.service.interfaces;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.dto.get.user.UserLoginDto;

public interface IUserService {
    UserDto register(UserRegisterDto user);
    UserDto refresh(UserLoginDto user);
    UserDto getUser(Long id);
}
