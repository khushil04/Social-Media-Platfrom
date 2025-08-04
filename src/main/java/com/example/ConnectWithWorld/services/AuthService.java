package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.dto.LoginDto;
import com.example.ConnectWithWorld.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDto loginDto) {
        // Authenticate the user using email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        // Cast the principal to your custom User entity
        User user = (User) authentication.getPrincipal();

        // Generate JWT token using user ID or any identifier
        return jwtService.generateToken(user);
    }
}
