package com.example.demo.service;

import com.example.demo.service.dto.SensorDto;
import com.example.demo.service.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public interface SensorService {

    Page<SensorDto> getAll(long limit, long offset, String filter);

    Page<SensorDto> getAll(long limit, long offset);

    SensorDto getById(long id);

    SensorDto create(@Valid SensorDto event);

    void update(@Valid SensorDto event);

    void delete(long id);

    boolean contains(long id);

    long countEntities();

    long countEntities(String filter);
}
