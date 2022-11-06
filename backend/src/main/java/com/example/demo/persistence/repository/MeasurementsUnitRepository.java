package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.MeasurementsUnit;

import java.util.List;

public interface MeasurementsUnitRepository {

    List<MeasurementsUnit> getAll();

    MeasurementsUnit getById(long id);

    MeasurementsUnit create(MeasurementsUnit unit);

    boolean update(MeasurementsUnit unit);

    boolean delete(long id);
}
