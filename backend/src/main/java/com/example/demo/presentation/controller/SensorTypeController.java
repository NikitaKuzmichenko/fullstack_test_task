package com.example.demo.presentation.controller;

import com.example.demo.service.SensorTypeService;
import com.example.demo.service.dto.SensorTypeDto;
import com.example.demo.service.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/sensorTypes",produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorTypeController {

    @Autowired
    private final SensorTypeService service;

    public SensorTypeController(SensorTypeService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<SensorTypeDto>> getAllSensorTypes(){
        return ResponseEntity.status(HttpStatus.OK).
                body(service.getAll());
    }
}
