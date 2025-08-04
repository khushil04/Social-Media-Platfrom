package com.example.ConnectWithWorld.controller;


import com.example.ConnectWithWorld.dto.LoginDto;
import com.example.ConnectWithWorld.dto.SignupDto;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.services.AuthService;
import com.example.ConnectWithWorld.services.UserUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserUserDetailsService userService;
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<User> signupDto(@RequestBody SignupDto signupDto) throws UserException{
        User user = userService.registerUser(signupDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }
}
