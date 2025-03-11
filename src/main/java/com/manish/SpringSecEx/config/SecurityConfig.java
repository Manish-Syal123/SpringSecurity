package com.manish.SpringSecEx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer ->customizer.disable());
        http.authorizeHttpRequests(request-> request
                .requestMatchers("register","login") //these two routs can be accessed without authentication
                .permitAll()
                .anyRequest().authenticated()); // no one should be able to access any page without authentication for any request.
        //http.formLogin(Customizer.withDefaults()); // creating a default login form for the user
        http.httpBasic(Customizer.withDefaults()); // enabling for Postman as well (just for testing Rest API's)
        http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // making it stateless http // every time(on every request) user will get new session id

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1= User.withDefaultPasswordEncoder()
//                .username("John")
//                .password("j@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2= User.withDefaultPasswordEncoder()
//                .username("Mark")
//                .password("m@123")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

    //step 1: user authobj goes to server.
    //step 2: server has Authentication manager.
    //step 3: Authentication manager call the AuthenticationProvider to do the work.
    //so here we are giving custom configuring to the AuthenticationProvider.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //nopasswordencode -> just plane text
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    //and here we are giving custom configuration to AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // getting the object of AuthenticationManager interface from the implementing class(AuthenticationConfiguration).
    }
}
