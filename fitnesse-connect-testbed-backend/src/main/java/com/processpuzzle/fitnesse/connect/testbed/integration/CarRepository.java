package com.processpuzzle.fitnesse.connect.testbed.integration;

import org.springframework.data.repository.CrudRepository;

import com.processpuzzle.fitnesse.connect.testbed.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

   Car findById( Long id );

   Iterable<Car> findByMakeIgnoringCase( String make );

}
