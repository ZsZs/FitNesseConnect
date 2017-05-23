package com.processpuzzle.fitnesse.connect.testbed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.processpuzzle.fitnesse.connect.testbed.application.RestApiController;
import com.processpuzzle.fitnesse.connect.testbed.domain.Car;
import com.processpuzzle.fitnesse.connect.testbed.integration.CarRepository;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestApiController( "cars" )
@ExposesResourceFor( Car.class )
public class CarService {
   private static final Logger logger = LoggerFactory.getLogger( CarService.class );
   private final CarRepository carRepository;

   @Autowired public CarService( CarRepository carRepository ) {
      this.carRepository = carRepository;
   }

   @PostMapping( value = "", produces = { MediaType.APPLICATION_JSON_VALUE } ) public ResponseEntity<Car> add( @RequestBody Car newCar ) {
      logger.info( "Adding new car to repository." );
      HttpHeaders headers = new HttpHeaders();
      headers.add( "Content-Type", "application/json; charset=utf-8" );
      return new ResponseEntity<Car>(carRepository.save( newCar ), headers, HttpStatus.CREATED );
   }

   @DeleteMapping( path = "/{id}" ) void delete( @PathVariable Long id ) {
      this.carRepository.delete( id );
   }

   @PatchMapping( path = "/{id}/color", produces = MediaType.APPLICATION_JSON_VALUE ) public Car changeColor( @PathVariable Long id, @RequestParam("color") String color ) {
      Car car = carRepository.findById( id );
      car.setColor( color );
      return carRepository.save( car );
   }

   @GetMapping( value = "", produces = MediaType.APPLICATION_JSON_VALUE ) public Iterable<Car> findAll() {
      return this.carRepository.findAll();
   }

   @GetMapping( path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE ) Car findById( @PathVariable Long id ) {
      Car foundCar = this.carRepository.findById( id );
      if( foundCar == null ){
         throw new CarNotFoundException( id );
      }
      
      return foundCar;
   }

   @GetMapping( path = "/make/{make}", produces = MediaType.APPLICATION_JSON_VALUE ) Iterable<Car> findByMake( @PathVariable String make ) {
      return carRepository.findByMakeIgnoringCase( make );
   }

   @RequestMapping( path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE ) public Car update( @PathVariable Long id, @RequestBody Car car ) {
      logger.info( "Updating car: " + id );
      Car carToUpdate = carRepository.findById( id );
      carToUpdate.update( car );
      return carToUpdate;
   }
}
