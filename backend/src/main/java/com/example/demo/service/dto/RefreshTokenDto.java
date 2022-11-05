package com.example.demo.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RefreshTokenDto implements Serializable {

    private long id;
    private long userId;
    private String value;
    private Date creationDate;
    private Date expirationDate;
}
