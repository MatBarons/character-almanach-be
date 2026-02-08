package com.character_almanach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.mappers.UserMapper;
import com.character_almanach.model.user.User;
import com.character_almanach.repository.UserRepository;
import com.character_almanach.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegisterDto user) {
        User entity = UserMapper.toEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(entity);
        return UserMapper.toDto(savedUser);
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
