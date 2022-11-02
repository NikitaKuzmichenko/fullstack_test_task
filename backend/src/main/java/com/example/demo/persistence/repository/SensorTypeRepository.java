package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.SensorType;

import java.util.List;

public interface SensorTypeRepository {

    List<SensorType> getAll();

    SensorType getById(long id);

    SensorType create(SensorType event);

    boolean update(SensorType event);

    boolean delete(long id);
}
