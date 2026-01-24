package com.character_almanach.service.interfaces;

import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.dto.get.user.UserLoginResponseDto;
import com.character_almanach.dto.create.UserRegisterDto;

public interface IUserService {
    UserLoginResponseDto login(UserLoginDto user);
    String register(UserRegisterDto user);
}
