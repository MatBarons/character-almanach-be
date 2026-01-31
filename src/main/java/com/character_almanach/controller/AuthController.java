package com.character_almanach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.dto.get.user.UserLoginResponseDto;
import com.character_almanach.model.user.CustomUserDetails;
import com.character_almanach.model.user.RefreshToken;
import com.character_almanach.service.JwtService;
import com.character_almanach.service.RefreshTokenService;
import com.character_almanach.service.UserService;



@RestController
@RequestMapping("/auth")
public class AuthController {  

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(@RequestBody UserLoginDto user) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
            )
        );
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new UserLoginResponseDto(token,refreshTokenService.createRefreshToken(userDetails.getDomainUser()));
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
