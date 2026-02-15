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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.character_almanach.dto.create.UserRegisterDto;
import com.character_almanach.dto.get.user.UserDto;
import com.character_almanach.dto.get.user.UserLoginDto;
import com.character_almanach.dto.get.user.UserLoginResponseDto;
import com.character_almanach.model.user.CustomUserDetails;
import com.character_almanach.model.user.User;
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
    @Autowired
    private UserService userService;
    @Autowired
    private UserService userDetailsService;

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
        return new UserLoginResponseDto(
            token,
            refreshTokenService.createRefreshToken(userDetails.getDomainUser()),
            userDetails.getId()
        );
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserLoginResponseDto register(@RequestBody UserRegisterDto user) {
        UserDto userDto = this.userService.register(user);
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

        String token = jwtService.generateToken(userDetails);
        return new UserLoginResponseDto(
            token,
            refreshTokenService.createRefreshToken(userDetails.getDomainUser()),
            userDetails.getId()
        );
    }

    
    @GetMapping("/refresh-token")
    public UserLoginResponseDto refreshToken(@RequestParam String token) {
        User user = refreshTokenService.verifyAndGet(token).getUser();
        String accessToken = jwtService.generateToken(new CustomUserDetails(user));
        return new UserLoginResponseDto(accessToken,token,user.getId());
    }
}
