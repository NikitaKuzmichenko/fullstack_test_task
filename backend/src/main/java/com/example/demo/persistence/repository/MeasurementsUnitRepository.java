package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.MeasurementsUnit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsUnitRepository {

    List<MeasurementsUnit> getAll();

    MeasurementsUnit getById(long id);

    MeasurementsUnit create(MeasurementsUnit event);

    boolean update(MeasurementsUnit event);

    boolean delete(long id);
}
