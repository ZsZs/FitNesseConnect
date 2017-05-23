package com.processpuzzle.fitnesse.connect.application;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
public class IntegratedApplicationTesterTest {
   private IntegratedApplicationTester applicationTester;

   @Before public void beforeEachTests() {
      applicationTester = new IntegratedApplicationTester();
      applicationTester.initialize( "unit-test" );
   }

   @Test public void initialize_configuresContext() {
      assertThat( IntegratedApplicationTester.getInstance().getConfiguration( "connector" ).getHost(), equalTo( "127.0.0.1" ) );
   }

   @Test public void getInstance_whenNotInstantiatedYet_createsInstance() {
      IntegratedApplicationTester applicationTester = IntegratedApplicationTester.getInstance();

      assertThat( applicationTester, notNullValue() );
      assertThat( applicationTester.getApplicationContext(), notNullValue() );
   }
}
