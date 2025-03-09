package com.manish.SpringSecEx.service;

import com.manish.SpringSecEx.model.UserPrincipal;
import com.manish.SpringSecEx.model.Users;
import com.manish.SpringSecEx.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);

        if(user==null){
            System.out.println("User not found!");
            throw  new UsernameNotFoundException("User not found!");
        }
        return new UserPrincipal(user); // this method return the UserDetails but it is an interface, so we have to create a class which implements UserDetails and return obj of that class.
    }
}
