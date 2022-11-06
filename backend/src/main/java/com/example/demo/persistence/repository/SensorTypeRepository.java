package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.SensorType;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SensorTypeRepository {

    List<SensorType> getAll();

    SensorType getById(long id);

    SensorType create(SensorType type);

    boolean update(SensorType type);

    boolean delete(long id);
}
