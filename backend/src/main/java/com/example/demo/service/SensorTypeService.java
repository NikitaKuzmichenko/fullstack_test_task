package com.example.demo.service;

import com.example.demo.service.dto.SensorTypeDto;
import com.example.demo.service.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public interface SensorTypeService {
    Page<SensorTypeDto> getAll();

    SensorTypeDto getById(long id);
}
