package com.example.demo.controllers;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Employee;
import com.example.demo.models.LoginRequest;
import com.example.demo.repositories.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= "*")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // login for the user.
    @PostMapping("/login")
    @CrossOrigin
    public String generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
            System.out.println(usernamePasswordAuthenticationToken);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            throw new Exception("Invalid username and password");
        }

        return jwtUtil.generateToken(loginRequest.getUserName());
    }
}
