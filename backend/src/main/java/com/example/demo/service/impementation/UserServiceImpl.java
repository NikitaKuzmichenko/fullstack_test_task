package com.example.demo.service.impementation;

import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.entity.UserRole;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.persistence.repository.UserRoleRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.dto.convertor.UserDtoConvertor;
import com.example.demo.service.exception.EntityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserRoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(@Valid UserDto user) {
        User tempUser = UserDtoConvertor.fromDto(user);
        UserRole role = roleRepository.getRoleByName(user.getRole());
        if(role == null){
            throw new EntityNotExistException(0,UserRole.class.getSimpleName());
        }
        tempUser.setRole(role);
        return UserDtoConvertor.toDto(userRepository.createUser(tempUser));
    }

    @Override
    public void update(@Valid UserDto user) {
        User tempUser = UserDtoConvertor.fromDto(user);
        UserRole role = roleRepository.getRoleByName(user.getRole());
        if(role == null){
            throw new EntityNotExistException(0,UserRole.class.getSimpleName());
        }
        tempUser.setRole(role);
        if(userRepository.updateUser(tempUser)){
            throw new EntityNotExistException(user.getId(),User.class.getSimpleName());
        }
    }

    @Override
    public UserDto createAndEncodePassword(@Valid UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return create(user);
    }

    @Override
    public UserDto getById(long id) {
        User user = userRepository.getById(id);
        if(user == null){
            throw new EntityNotExistException(id,User.class.getSimpleName());
        }
        return UserDtoConvertor.toDto(user);
    }

    @Override
    public UserDto getByLogin(String login) {
        User user = userRepository.getUserByLogin(login);
        if(user == null){
            throw new EntityNotExistException(0,User.class.getSimpleName());
        }
        return UserDtoConvertor.toDto(user);
    }

    @Override
    public void delete(long id) {
        if(userRepository.deleteUserById(id)){
            throw new EntityNotExistException(id,User.class.getSimpleName());
        }
    }
}
