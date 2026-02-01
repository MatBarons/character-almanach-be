package com.character_almanach.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.mappers.UserMapper;
import com.character_almanach.repository.UserRepository;
import com.character_almanach.service.interfaces.IUserService;

public class UserService implements IUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public String register(UserRegisterDto user) {
        // Implement registration logic here
        return "Registration successful";
    }
    
    @Override
    public UserDto getUser(Long id){
        return UserMapper.toDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserDto refresh(UserLoginDto user){
        return UserMapper.toDto(userRepository.findByUsername(user.getUsername()).orElseThrow());
    }
    
}
