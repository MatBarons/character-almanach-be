package com.character_almanach.service;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.service.interfaces.IUserService;

public class UserService implements IUserService {
    

    @Override
    public String register(UserRegisterDto user) {
        // Implement registration logic here
        return "Registration successful";
    }
    
}
