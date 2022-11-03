package com.example.demo;

import com.example.demo.persistence.repository.SensorRepository;
import com.example.demo.service.SensorService;
import com.example.demo.service.dto.MeasurementsUnitDto;
import com.example.demo.service.dto.SensorDto;
import com.example.demo.service.dto.SensorTypeDto;
import com.example.demo.service.dto.convertor.MeasurementsUnitDtoConvertor;
import com.example.demo.service.dto.convertor.SensorTypeDtoConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemoApplication implements CommandLineRunner {

	@Autowired
	SensorService repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SensorDto dto = new SensorDto();

		MeasurementsUnitDto mu = new MeasurementsUnitDto();
		mu.setId(9);

		SensorTypeDto st = new SensorTypeDto();
		st.setId(9);

		dto.setName("name1");
		dto.setModel("model1");
		dto.setLowerOperationalBound(1);
		dto.setHigherOperationalBound(2);
		dto.setMeasurementsUnit(mu);
		dto.setType(st);
		dto.setLocation("location1");
		dto.setDescription("desc1");

		System.out.println(repository.create(dto));
	}
}
