package com.example.demo.presentation.controller;

import com.example.demo.service.SensorService;
import com.example.demo.service.dto.SensorDto;
import com.example.demo.service.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/sensors",produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorController {

    @Autowired
    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('Administrator') or hasAuthority('Viewer')")
    public ResponseEntity<Page<SensorDto>> getAllSensors(@RequestParam(required = false) Long offset,
                                                        @RequestParam(required = false) Long limit,
                                                        @RequestParam(required = false) String fieldFilter) {
        return ResponseEntity.status(HttpStatus.OK).
                body(service.getAll(
                        limit == null ? 0 : limit,
                        offset == null ? 0 : offset,
                        fieldFilter));
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<SensorDto> getSensorById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).
                body(service.getById(id));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<SensorDto> createNewSensor(@RequestBody SensorDto event) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(service.create(event));
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<?> putSensor(@PathVariable("id") long id, @RequestBody SensorDto event) {
        if(service.contains(id)){
            event.setId(id);
            service.update(event);
            return ResponseEntity.status(HttpStatus.OK).
                    build();
        }
        else {
            event.setId(id);
            service.create(event);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(service.create(event));
        }
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<?> deleteSensorById(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).
                build();
    }

}
