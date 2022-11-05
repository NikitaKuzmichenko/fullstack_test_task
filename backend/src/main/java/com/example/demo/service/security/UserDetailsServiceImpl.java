package com.example.demo.service.security;

import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto userDto = userService.getByLogin(login);

        if (userDto == null) {
            throw new UsernameNotFoundException("User with login = " + login + " not found");
        }

        return new User(
                userDto.getLogin(),
                userDto.getPassword(),
                Set.of(new SimpleGrantedAuthority(userDto.getRole())));
    }
}
