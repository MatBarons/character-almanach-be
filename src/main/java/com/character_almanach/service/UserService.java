package com.character_almanach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.exception.user.UserAlreadyExistsException;
import com.character_almanach.exception.user.UserNotFoundException;
import com.character_almanach.mappers.UserMapper;
import com.character_almanach.model.user.User;
import com.character_almanach.repository.UserRepository;
import com.character_almanach.service.interfaces.IUserService;

import jakarta.validation.Valid;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(@Valid UserRegisterDto user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException("User with username '" + user.getUsername() + "' already exists.");
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException("User with email '" + user.getEmail() + "' already exists.");
        });
        User entity = UserMapper.toUserEntity(user);
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
        return UserMapper.toDto(userRepository.findByUsername(user.getUsername()).orElseThrow(
            () -> new UserNotFoundException(user.getUsername())
        ));
    }
    
}
