package com.example.demo.service;

import com.example.demo.service.dto.MeasurementsUnitDto;
import com.example.demo.service.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public interface MeasurementsUnitService {
    Page<MeasurementsUnitDto> getAll();

    MeasurementsUnitDto getById(long id);
}
