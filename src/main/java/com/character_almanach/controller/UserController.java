package com.character_almanach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.dto.get.user.UserLoginResponseDto;
import com.character_almanach.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {  

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(@RequestBody UserLoginDto user) {
        return this.userService.login(user);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody UserRegisterDto user) {
        return this.userService.register(user);
    }
    
    @GetMapping("/refresh-token")
    public String refreshToken() {
        return "Token refreshed";
    }
}
