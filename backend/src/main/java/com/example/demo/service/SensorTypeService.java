package com.example.demo.service;

import com.example.demo.service.dto.SensorTypeDto;
import com.example.demo.service.pagination.Page;

public interface SensorTypeService {
    Page<SensorTypeDto> getAll();
}
