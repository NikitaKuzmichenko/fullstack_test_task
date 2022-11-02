package com.example.demo.service.impementation;

import com.example.demo.persistence.repository.MeasurementsUnitRepository;
import com.example.demo.service.MeasurementsUnitService;
import com.example.demo.service.dto.MeasurementsUnitDto;
import com.example.demo.service.dto.SensorDto;
import com.example.demo.service.dto.convertor.MeasurementsUnitDtoConvertor;
import com.example.demo.service.dto.convertor.SensorDtoConvertor;
import com.example.demo.service.pagination.Page;
import com.example.demo.service.pagination.implementation.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class MeasurementsUnitServiceImpl implements MeasurementsUnitService {

    @Autowired
    private final MeasurementsUnitRepository repository;

    public MeasurementsUnitServiceImpl(MeasurementsUnitRepository repository) {
        this.repository = repository;
    }


    @Override
    public Page<MeasurementsUnitDto> getAll() {
        List<MeasurementsUnitDto> result = repository.getAll().stream().
                map(MeasurementsUnitDtoConvertor::toDto).
                collect(Collectors.toList());

        return new PageImpl<>(result,0,result.size());
    }
}
