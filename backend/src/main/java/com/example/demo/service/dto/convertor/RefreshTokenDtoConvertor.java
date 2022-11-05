package com.example.demo.service.dto.convertor;

import com.example.demo.persistence.entity.RefreshToken;
import com.example.demo.persistence.entity.User;
import com.example.demo.service.dto.RefreshTokenDto;

public final class RefreshTokenDtoConvertor {

    private RefreshTokenDtoConvertor(){}

    public static RefreshTokenDto toDto(RefreshToken token){
        if(token == null){
            return null;
        }
        RefreshTokenDto dto = new RefreshTokenDto();
        dto.setCreationDate(token.getCreationDate());
        dto.setExpirationDate(token.getExpirationDate());
        dto.setId(token.getId());
        dto.setValue(token.getValue());
        if(token.getUserId() != null) {
            dto.setUserId(token.getUserId().getId());
        }
        return dto;
    }

    public static RefreshToken fromDto(RefreshTokenDto token){
        if(token == null){
            return null;
        }
        RefreshToken entity = new RefreshToken();
        entity.setCreationDate(token.getCreationDate());
        entity.setExpirationDate(token.getExpirationDate());
        entity.setId(token.getId());
        entity.setValue(token.getValue());
        User user = new User();
        user.setId(token.getUserId());
        entity.setUserId(user);
        return entity;
    }

}
