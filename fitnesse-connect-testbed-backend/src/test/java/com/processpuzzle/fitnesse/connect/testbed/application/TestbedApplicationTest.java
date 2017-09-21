package com.processpuzzle.fitnesse.connect.testbed.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith( SpringRunner.class )
@SpringBootTest( classes = { TestbedApplication.class } )
@ActiveProfiles( "unit-test" )
public class TestbedApplicationTest {

   @Test public void contextLoads() throws Exception {      
   }   
}
