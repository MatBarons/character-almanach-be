package com.character_almanach.service.interfaces;

import com.character_almanach.dto.create.UserRegisterDto;

public interface IUserService {
    String register(UserRegisterDto user);
}
