package com.processpuzzle.fitnesse.connect.testbed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.processpuzzle.fitnesse.connect.testbed.domain.Car;
import com.processpuzzle.fitnesse.connect.testbed.integration.CarRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping( "/cars" )
@ExposesResourceFor( Car.class )
public class CarService {
   private final CarRepository carRepository;

   @Autowired public CarService( CarRepository carRepository ) {
      this.carRepository = carRepository;
   }

   @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE ) public Car add( @RequestBody Car newCar ){
      return carRepository.save(  newCar );
   }   

   @DeleteMapping( path = "/{id}" ) void delete( @PathVariable Long id ) {
      this.carRepository.delete( id );
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

   @PutMapping( path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE ) public Car update( @PathVariable Long id, @RequestBody Car car ){
      return carRepository.save( car );
   }   
}
