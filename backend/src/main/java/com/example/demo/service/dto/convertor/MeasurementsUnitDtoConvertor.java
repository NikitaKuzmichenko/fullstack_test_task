package com.example.demo.service.dto.convertor;

import com.example.demo.persistence.entity.MeasurementsUnit;
import com.example.demo.service.dto.MeasurementsUnitDto;

public final class MeasurementsUnitDtoConvertor {

    private MeasurementsUnitDtoConvertor(){}

    public static MeasurementsUnitDto toDto(MeasurementsUnit unit){
        if(unit == null){
            return null;
        }

        MeasurementsUnitDto dto = new MeasurementsUnitDto();
        dto.setId(unit.getId());
        dto.setValue(unit.getValue());
        return dto;
    }

    public static MeasurementsUnit fromDto(MeasurementsUnitDto unit){
        if(unit == null){
            return null;
        }

        MeasurementsUnit entity = new MeasurementsUnit();
        entity.setId(unit.getId());
        entity.setValue(unit.getValue());
        return entity;
    }
}
