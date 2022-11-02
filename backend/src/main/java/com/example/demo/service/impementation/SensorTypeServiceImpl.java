package com.example.demo.service.impementation;

import com.example.demo.persistence.repository.SensorTypeRepository;
import com.example.demo.service.SensorTypeService;
import com.example.demo.service.dto.SensorTypeDto;
import com.example.demo.service.dto.convertor.SensorTypeDtoConvertor;
import com.example.demo.service.pagination.Page;
import com.example.demo.service.pagination.implementation.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class SensorTypeServiceImpl implements SensorTypeService {

    @Autowired
    private final SensorTypeRepository repository;

    public SensorTypeServiceImpl(SensorTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<SensorTypeDto> getAll() {
        List<SensorTypeDto> result = repository.getAll().stream().
                map(SensorTypeDtoConvertor::toDto).
                collect(Collectors.toList());

        return new PageImpl<>(result,0,result.size());
    }
}
