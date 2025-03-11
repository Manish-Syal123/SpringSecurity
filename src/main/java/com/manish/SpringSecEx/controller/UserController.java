package com.manish.SpringSecEx.controller;

import com.manish.SpringSecEx.model.Users;
import com.manish.SpringSecEx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return service.verify(user);
    }
}
