package com.example.demo.service.impementation;

import com.example.demo.persistence.entity.RefreshToken;
import com.example.demo.persistence.repository.RefreshTokenRepository;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.RefreshTokenDto;
import com.example.demo.service.dto.convertor.RefreshTokenDtoConvertor;
import com.example.demo.service.dto.convertor.UserDtoConvertor;
import com.example.demo.service.exception.EntityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Transactional
@Validated
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private final RefreshTokenRepository repository;

    @Autowired private final UserService userService;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public RefreshTokenDto create(@Valid  RefreshTokenDto token) {
        RefreshToken tempToken = RefreshTokenDtoConvertor.fromDto(token);
        tempToken.setUserId(UserDtoConvertor.fromDto(userService.getById(token.getUserId())));

        return RefreshTokenDtoConvertor.toDto(repository.create(tempToken));
    }

    @Override
    public void update(@Valid RefreshTokenDto token) {
        RefreshToken tempToken = RefreshTokenDtoConvertor.fromDto(token);
        tempToken.setUserId(UserDtoConvertor.fromDto(userService.getById(token.getUserId())));

        if(!repository.updateToken(tempToken)){
            throw new EntityNotExistException(token.getId(), RefreshToken.class.getSimpleName());
        }
    }

    @Override
    public RefreshTokenDto getById(long id) {
        RefreshToken token = repository.getByUserId(id);
        if(token == null){
            throw new EntityNotExistException(id,RefreshToken.class.getSimpleName());
        }
        return RefreshTokenDtoConvertor.toDto(token);
    }

    @Override
    public RefreshTokenDto getByUserLogin(String login) {
        RefreshToken token = repository.getByUserLogin(login);
        if(token == null){
            throw new EntityNotExistException(0,RefreshToken.class.getSimpleName());
        }
        return RefreshTokenDtoConvertor.toDto(token);
    }

    @Override
    public RefreshTokenDto getByUserId(long id) {
        RefreshToken token = repository.getByUserId(id);
        if(token == null){
            throw new EntityNotExistException(0,RefreshToken.class.getSimpleName());
        }
        return RefreshTokenDtoConvertor.toDto(token);
    }

    @Override
    public RefreshTokenDto getByToken(String strToken) {
        RefreshToken token = repository.getByToken(strToken);
        if(token == null){
            throw new EntityNotExistException(0,RefreshToken.class.getSimpleName());
        }
        return RefreshTokenDtoConvertor.toDto(token);
    }
}
