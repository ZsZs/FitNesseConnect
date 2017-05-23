package com.processpuzzle.fitnesse.connect.testbed.integration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.processpuzzle.fitnesse.connect.testbed.domain.Car;

@RunWith( SpringRunner.class )
@ContextConfiguration( classes = { CarRepository.class } )
@EntityScan( basePackages = "com.processpuzzle.fitnesse.connect.testbed.domain" )
@DataJpaTest
@ActiveProfiles( "unit-test" )
public class CarRepositoryTest {
   @Autowired private CarRepository carRepository;
   @Autowired private TestEntityManager entityManager;
   private Car newCar;
   private Car persistedCar;

   @Before public void beforeEachTests() {
      newCar = new Car( "Ford", "Shelby Mustang", 1965, "green" );
      persistedCar = new Car( "Jaguar", "XRJ", 2015, "grey" );

      entityManager.persist( persistedCar );
   }

   @After public void afterEachTest() {
      entityManager.remove( persistedCar );
   }

   @Test public void save_whenCarIsNew_adds() {
      assumeThat( newCar.getId(), nullValue() );

      carRepository.save( newCar );

      assertThat( newCar.getId(), notNullValue() );
   }

   @Test public void save_whenCarAlreadyPersisted_updates() {
      assumeThat( persistedCar.getId(), notNullValue() );
      assumeThat( carRepository.findAll().size(), equalTo( 1 ));

      persistedCar.update( newCar );
      carRepository.save( persistedCar );

      assertThat( carRepository.findById( persistedCar.getId() ).getId(), equalTo( persistedCar.getId() ));
      assertThat( carRepository.findAll().size(), equalTo( 1 ));
   }
}
