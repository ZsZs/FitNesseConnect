package com.processpuzzle.fitnesse.connect.application;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties( prefix = "connector" )
public class ConnectorApplicationConfiguration extends ApplicationConfiguration {
   
   @PostConstruct public void postConstruct(){
      IntegratedApplicationTester.getInstance().addConfiguration( "connector", this );
   }
}
