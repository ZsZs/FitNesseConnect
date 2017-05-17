package com.processpuzzle.fitnesse.connect.application;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest( classes={ IntegratedApplicationTester.class, ConnectorApplicationConfiguration.class })
@EnableConfigurationProperties
@ActiveProfiles( "unit-test" )
public class ApplicationConfigurationTest {
   @Autowired ConnectorApplicationConfiguration applicationConfiguration;
   
   @Test public void autoconfiguration_selectsServiceSpecificProperties(){
      assertThat( applicationConfiguration.getHost(), equalTo( "127.0.0.1" ));
      assertThat( applicationConfiguration.getPort(), equalTo( "8080" ));
      assertThat( applicationConfiguration.getContextRoot(), equalTo( "dev" ));
      assertThat( applicationConfiguration.buildHostUrl(), equalTo( "http://127.0.0.1:8080/dev" ));
   }
   
   @Test public void autoconfiguration_selectsNestedDataConfiguration(){
      assertThat( applicationConfiguration.getDataSourceConfiguration(), notNullValue());
      
      assertThat( applicationConfiguration.getDatasourceDriverClass(), equalTo( "org.h2.Driver" ));
      assertThat( applicationConfiguration.getDatasourceUrl(), equalTo( "jdbc:h2:mem:test_mem" ));
      assertThat( applicationConfiguration.getDatasourceUser(), equalTo( "SA" ));
      assertThat( applicationConfiguration.getDatasourcePassword(), equalTo( "" ));
   }
   
   @Test public void createJdbcTemplate_configuresServerSpecificDataSource() throws SQLException{
      assertThat( applicationConfiguration.createJdbcTemplate(), notNullValue() );
      assertThat( applicationConfiguration.createJdbcTemplate().getDataSource(), notNullValue() );
      
      DataSource dataSource = applicationConfiguration.createJdbcTemplate().getDataSource();
      assertThat( dataSource.toString(), containsString( "driverClassName=org.h2.Driver;" ));  
   }
   
   @Test public void createRestClient_configuresServerSpecificHost(){
      assertThat( applicationConfiguration.createRestClient(), notNullValue() );
   }
}
