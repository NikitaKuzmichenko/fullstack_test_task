package com.example.demo.service.security;

import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.exception.EntityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto userDto;
        try {
            userDto = userService.getByLogin(login);
        }catch (EntityNotExistException e){
            throw new UsernameNotFoundException("User with login = " + login + " not found");
        }

        return new CustomUserDetails(
                userDto.getId(),
                userDto.getLogin(),
                userDto.getPassword(),
                Set.of(new SimpleGrantedAuthority(userDto.getRole())));
    }
}
