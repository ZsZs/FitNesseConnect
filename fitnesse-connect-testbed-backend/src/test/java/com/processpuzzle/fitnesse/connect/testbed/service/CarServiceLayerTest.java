package com.processpuzzle.fitnesse.connect.testbed.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.testbed.application.TestbedApplication;
import com.processpuzzle.fitnesse.connect.testbed.domain.Car;
import com.processpuzzle.fitnesse.connect.testbed.integration.CarRepository;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { TestbedApplication.class, RestServiceExceptionHandler.class } )
@AutoConfigureMockMvc
public class CarServiceLayerTest {
   private static final String CAR_INVALID_ID = "NotCarId";
   private static final String RESOURCE_PATH = "/api/cars";
   private static final long CAR_NOT_EXISTS_ID = 1000l;
   private static final long CAR_TWO_ID = 2l;
   private static final long CAR_ONE_ID = 1l;
   @MockBean private CarRepository carRepository;
   private Car carNew;
   private Car carOne;
   private Car carTwo;
   private List<Car> cars;
   @Autowired private ObjectMapper jsonMapper;
   @Autowired private MockMvc mockMvc;

   @Before public void beforeEachTests(){
      carNew = new Car( "BMW", "Z4", 2012, "red" );
      carOne = new Car( "Honda", "Accord", 2016, "blue" );
      carTwo = new Car( "Mercedes-Benz", "CLS", 2017, "maroon" );
      cars = Lists.newArrayList();
      cars.add( carOne );
      cars.add( carTwo );
      
      when( carRepository.findAll() ).thenReturn( cars );
      when( carRepository.findById( CAR_ONE_ID ) ).thenReturn( carOne );
      when( carRepository.findById( CAR_TWO_ID ) ).thenReturn( carTwo );
      when( carRepository.findById( CAR_NOT_EXISTS_ID ) ).thenReturn( null );
      when( carRepository.save( carNew ) ).thenReturn( carNew );
      when( carRepository.save( carOne ) ).thenReturn( carOne );
   }
   
   @Test public void add_savesNewCar() throws Exception{
      // @formatter:off
      this.mockMvc.perform( post( RESOURCE_PATH )
                            .contentType( MediaType.APPLICATION_JSON_VALUE )
                            .content( jsonMapper.writeValueAsString( carNew ) ))
                  .andExpect( status().isCreated() )
                  .andExpect( content().contentTypeCompatibleWith("application/json"));
      // @formatter:on
   }
   
   @Test public void changeColor_updateCharsColor() throws Exception{
      // @formatter:off
      this.mockMvc.perform( patch( RESOURCE_PATH + "/" + CAR_ONE_ID + "/color")
                            .param( "color", "purple" ))
                  .andExpect( status().isOk() );
      // @formatter:on
   }
   
   @Test public void findAll_whenNoFilterIsGiven_returnsAllCars() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( RESOURCE_PATH ) )
                  .andExpect( status().isOk() )
                  .andExpect( content().json( jsonMapper.writeValueAsString( new Object[] { carOne, carTwo })));
      // @formatter:on
   }
   
   @Test public void findById_whenIdIsValid_returnsOneCar() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( RESOURCE_PATH + "/" + CAR_ONE_ID ))
                  .andExpect( status().isOk() )
                  .andExpect( content().json( jsonMapper.writeValueAsString( carOne ) ));
      // @formatter:on
   }
   
   @Test public void findById_whenCarNotExist_returns404() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( RESOURCE_PATH + "/" + CAR_NOT_EXISTS_ID ) )
                  .andExpect( status().isNotFound() );
      // @formatter:on
   }
   
   @Test public void findById_whenIdIsWrong_returns400() throws Exception{
      // @formatter:off
      this.mockMvc.perform( get( RESOURCE_PATH + "/" + CAR_INVALID_ID ) )
                  .andExpect( status().isBadRequest() );
      // @formatter:on
   }
   
   @Test public void update_persitsChanges() throws Exception{
      carOne.setYear( 2000 );
      // @formatter:off
      this.mockMvc.perform( put( RESOURCE_PATH + "/" + CAR_ONE_ID )
                            .contentType( MediaType.APPLICATION_JSON_VALUE )
                            .content( jsonMapper.writeValueAsString( carOne ) ))
                  .andExpect( status().isOk() );
      // @formatter:on
   }
}
