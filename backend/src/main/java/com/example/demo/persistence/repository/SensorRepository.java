package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Sensor;

import java.util.List;

public interface SensorRepository {

    List<Sensor> getAll(long limit, long offset, String allFieldsPredicate);

    List<Sensor> getAll(long limit,long offset);

    long countAll();

    long countAll(String allFieldsPredicate);

    Sensor getById(long id);

    Sensor create(Sensor event);

    boolean update(Sensor event);

    boolean delete(long id);
}
