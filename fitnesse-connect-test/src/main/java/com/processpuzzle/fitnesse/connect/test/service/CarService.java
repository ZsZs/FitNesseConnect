package com.processpuzzle.fitnesse.connect.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.processpuzzle.fitnesse.connect.test.domain.Car;
import com.processpuzzle.fitnesse.connect.test.integration.CarRepository;

@RestController
@RequestMapping( "/cars" )
@ExposesResourceFor( Car.class )
public class CarService {
   private final CarRepository carRepository;

   @Autowired public CarService( CarRepository carRepository ) {
      this.carRepository = carRepository;
   }

   @GetMapping( value = "", produces = MediaType.APPLICATION_JSON_VALUE ) public Iterable<Car> findAll() {
      return this.carRepository.findAll();
   }

   @GetMapping( path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE ) Car findById( @PathVariable Long id ) {
      return this.carRepository.findById( id );
   }

   @GetMapping( path = "/make/{make}", produces = MediaType.APPLICATION_JSON_VALUE ) Iterable<Car> findByMake( @PathVariable String make ) {
      return carRepository.findByMakeIgnoringCase( make );
   }

}
