package com.example.demo.service.impementation;

import com.example.demo.persistence.entity.Sensor;
import com.example.demo.persistence.repository.SensorRepository;
import com.example.demo.service.MeasurementsUnitService;
import com.example.demo.service.SensorService;
import com.example.demo.service.SensorTypeService;
import com.example.demo.service.dto.SensorDto;
import com.example.demo.service.dto.convertor.SensorDtoConvertor;
import com.example.demo.service.exception.EntityNotExistException;
import com.example.demo.service.pagination.Page;
import com.example.demo.service.pagination.implementation.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class SensorServiceImpl implements SensorService {

    private static final long DEFAULT_LIMIT = 10;
    private static final long DEFAULT_OFFSET = 0;

    @Autowired
    private final SensorRepository repository;

    @Autowired
    private final MeasurementsUnitService measurementsUnitService;

    @Autowired
    private final SensorTypeService sensorTypeService;

    public SensorServiceImpl(SensorRepository repository,
                             MeasurementsUnitService measurementsUnitService,
                             SensorTypeService sensorTypeService) {
        this.repository = repository;
        this.measurementsUnitService = measurementsUnitService;
        this.sensorTypeService = sensorTypeService;
    }

    @Override
    public Page<SensorDto> getAll(long limit, long offset, String filter) {
        if(filter == null){
            return getAll(limit,offset);
        }

        long resultLimit = limit <= 0 ? DEFAULT_LIMIT : limit;
        long resultOffset = offset <= 0 ? DEFAULT_OFFSET : offset;

        List<SensorDto> result = repository.getAll(resultLimit, resultOffset, filter).stream().
                map(SensorDtoConvertor::toDto).
                collect(Collectors.toList());

        return new PageImpl<>(result,resultOffset,countEntities(filter));
    }

    @Override
    public Page<SensorDto> getAll(long limit, long offset) {
        long resultLimit = limit <= 0 ? DEFAULT_LIMIT : limit;
        long resultOffset = offset <= 0 ? DEFAULT_OFFSET : offset;

        List<SensorDto> result = repository.getAll(resultLimit, resultOffset).stream().
                map(SensorDtoConvertor::toDto).
                collect(Collectors.toList());

        return new PageImpl<>(result,resultOffset,countEntities());
    }

    @Override
    public SensorDto getById(long id) {
        SensorDto event = SensorDtoConvertor.toDto(repository.getById(id));
        if(event == null){
            throw new EntityNotExistException("id = " + id, Sensor.class.getSimpleName());
        }
        return event;
    }

    @Override
    public SensorDto create(@Valid SensorDto sensor) {
        setRelatedEntities(sensor);

        return SensorDtoConvertor.toDto(repository.create(SensorDtoConvertor.fromDto(sensor)));
    }

    @Override
    public void update(@Valid SensorDto sensor) {
        setRelatedEntities(sensor);

        if(!repository.update(SensorDtoConvertor.fromDto(sensor))){
            throw new EntityNotExistException("id = " + sensor.getId(), Sensor.class.getSimpleName());
        }
    }

    @Override
    public void delete(long id) {
        if(!repository.delete(id)){
            throw new EntityNotExistException("id = " + id, Sensor.class.getSimpleName());
        }
    }

    @Override
    public boolean contains(long id) {
        return repository.getById(id) != null;
    }

    @Override
    public long countEntities() {
        return repository.countAll();
    }

    @Override
    public long countEntities(String filter) {
        if(filter == null){
            return countEntities();
        }
        return repository.countAll(filter);
    }

    private void setRelatedEntities(SensorDto sensor){
        sensor.setMeasurementsUnit(measurementsUnitService.getById(sensor.getMeasurementsUnit().getId()));
        sensor.setType(sensorTypeService.getById(sensor.getType().getId()));
    }

}
