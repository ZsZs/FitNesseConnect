package com.processpuzzle.fitnesse.connect.testbed.integration;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.processpuzzle.fitnesse.connect.testbed.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

   Car findById( Long id );
   List<Car> findAll();
   Iterable<Car> findByMakeIgnoringCase( String make );

}
