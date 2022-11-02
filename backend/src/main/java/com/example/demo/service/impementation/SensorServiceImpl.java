package com.example.demo.service.impementation;

import com.example.demo.persistence.entity.Sensor;
import com.example.demo.persistence.repository.SensorRepository;
import com.example.demo.service.SensorService;
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

    public SensorServiceImpl(SensorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<SensorDto> getAll(long limit, long offset, String filter) {
        if(filter == null){
            return getAll(limit,offset);
        }

        long resultLimit = limit < 0 ? DEFAULT_LIMIT : limit;
        long resultOffset = offset < 0 ? DEFAULT_OFFSET : offset;

        List<SensorDto> result = repository.getAll(resultLimit, resultOffset, filter).stream().
                map(SensorDtoConvertor::toDto).
                collect(Collectors.toList());

        return new PageImpl<>(result,resultOffset,countEntities(filter));
    }

    @Override
    public Page<SensorDto> getAll(long limit, long offset) {
        long resultLimit = limit < 0 ? DEFAULT_LIMIT : limit;
        long resultOffset = offset < 0 ? DEFAULT_OFFSET : offset;

        List<SensorDto> result = repository.getAll(resultLimit, resultOffset).stream().
                map(SensorDtoConvertor::toDto).
                collect(Collectors.toList());

        return new PageImpl<>(result,resultOffset,countEntities());
    }

    @Override
    public SensorDto getById(long id) {
        SensorDto event = SensorDtoConvertor.toDto(repository.getById(id));
        if(event == null){
            throw new EntityNotExistException(id, Sensor.class.getSimpleName());
        }
        return event;
    }

    @Override
    public SensorDto create(@Valid SensorDto event) {
        return SensorDtoConvertor.toDto(repository.create(SensorDtoConvertor.fromDto(event)));
    }

    @Override
    public void update(@Valid SensorDto event) {
        if(!repository.update(SensorDtoConvertor.fromDto(event))){
            throw new EntityNotExistException(event.getId(), Sensor.class.getSimpleName());
        }
    }

    @Override
    public void delete(long id) {
        if(!repository.delete(id)){
            throw new EntityNotExistException(id, Sensor.class.getSimpleName());
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

}
