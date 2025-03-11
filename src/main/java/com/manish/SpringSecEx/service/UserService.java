package com.manish.SpringSecEx.service;

import com.manish.SpringSecEx.model.Users;
import com.manish.SpringSecEx.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authManager;
    public Users register(Users user){
        return repo.save(user);

    }

    public String verify(Users user) {
        // so we are passing an un-authenticated object to authManager, and it will give us an authenticated object
        // this will automatically verify the user is present in the db or not and return its obj if present in db.
        Authentication authentication=
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return "Success";
        }

        return "Fail";
    }
}
