package com.example.demo.service;

import com.example.demo.service.dto.MeasurementsUnitDto;
import com.example.demo.service.pagination.Page;

public interface MeasurementsUnitService {

    Page<MeasurementsUnitDto> getAll();
}
