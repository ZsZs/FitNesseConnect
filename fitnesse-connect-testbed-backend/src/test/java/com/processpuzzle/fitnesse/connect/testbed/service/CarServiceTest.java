package com.processpuzzle.fitnesse.connect.testbed.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.testbed.application.TestbedApplication;
import com.processpuzzle.fitnesse.connect.testbed.domain.Car;
import com.processpuzzle.fitnesse.connect.testbed.integration.CarRepository;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { TestbedApplication.class } )
@AutoConfigureMockMvc
public class CarServiceTest {
   private static final long CAR_NOT_EXISTS_ID = 1000l;
   private static final long CAR_TWO_ID = 2l;
   private static final long CAR_ONE_ID = 1l;
   @MockBean private CarRepository carRepository;
   private Car carOne;
   private Car carTwo;
   private List<Car> cars;
   @Autowired private ObjectMapper jsonMapper;
   @Autowired private MockMvc mockMvc;

   @Before public void beforeEachTests(){
      carOne = new Car( "Honda", "Accord", 2016, "blue" );
      carTwo = new Car( "Mercedes-Benz", "CLS", 2017, "maroon" );
      cars = Lists.newArrayList();
      cars.add( carOne );
      cars.add( carTwo );
      
      when( carRepository.findAll() ).thenReturn( cars );
      when( carRepository.findById( CAR_ONE_ID ) ).thenReturn( carOne );
      when( carRepository.findById( CAR_TWO_ID ) ).thenReturn( carTwo );
      when( carRepository.findById( CAR_NOT_EXISTS_ID ) ).thenReturn( null );
   }
   
   @Test public void findAll_whenNoFilterIsGiven_returnsAllCars() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( "/api/cars" ) )
                  .andExpect( status().isOk() )
                  .andExpect( content().json( jsonMapper.writeValueAsString( new Object[] { carOne, carTwo })));
      // @formatter:on
   }
   
   @Test public void findById_whenIdIsValid_returnsOneCar() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( "/api/cars/" + CAR_ONE_ID ))
                  .andExpect( status().isOk() )
                  .andExpect( content().json( jsonMapper.writeValueAsString( carOne ) ));
      // @formatter:on
   }
   
   @Test public void findById_whenCarNotExist_returns404() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( "/api/cars/" + CAR_NOT_EXISTS_ID ) )
                  .andExpect( status().isNotFound() );
      // @formatter:on
   }
   
   @Test public void findById_whenIdIsWrong_returns400() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( "/api/cars/NotCarId" ) )
                  .andExpect( status().isBadRequest() );
      // @formatter:on
   }
}
