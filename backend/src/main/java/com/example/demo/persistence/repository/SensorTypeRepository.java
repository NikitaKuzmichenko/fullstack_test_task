package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.SensorType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorTypeRepository {

    List<SensorType> getAll();

    SensorType getById(long id);

    SensorType create(SensorType event);

    boolean update(SensorType event);

    boolean delete(long id);
}
