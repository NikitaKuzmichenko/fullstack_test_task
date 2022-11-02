package com.example.demo.service.dto.convertor;

import com.example.demo.persistence.entity.SensorType;
import com.example.demo.service.dto.SensorTypeDto;

public final class SensorTypeDtoConvertor {
    private SensorTypeDtoConvertor(){}

    public static SensorTypeDto toDto(SensorType type){
        if(type == null){
            return null;
        }

        SensorTypeDto dto = new SensorTypeDto();
        dto.setId(type.getId());
        dto.setValue(type.getValue());
        return dto;
    }

    public static SensorType fromDto(SensorTypeDto type){
        if(type == null){
            return null;
        }

        SensorType entity = new SensorType();
        entity.setId(type.getId());
        entity.setValue(type.getValue());
        return entity;
    }
}