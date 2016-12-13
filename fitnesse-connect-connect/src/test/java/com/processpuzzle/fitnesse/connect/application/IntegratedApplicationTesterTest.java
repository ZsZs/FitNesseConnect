package com.processpuzzle.fitnesse.connect.application;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
public class IntegratedApplicationTesterTest {

   @Test public void initialize_configuresContext(){
      IntegratedApplicationTester applicationTester = new IntegratedApplicationTester();
      applicationTester.initialize( "unit-test" );
      
      assertThat( IntegratedApplicationTester.getInstance().getConfiguration( "connector" ).getHost(), equalTo( "127.0.0.1" ));
   }
}
