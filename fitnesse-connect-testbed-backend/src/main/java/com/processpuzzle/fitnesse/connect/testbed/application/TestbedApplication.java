package com.processpuzzle.fitnesse.connect.testbed.application;

import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.processpuzzle.fitnesse.connect.testbed.file.StorageProperties;

@SpringBootApplication
@Import( TestbedApplicationConfiguration.class )
@ComponentScan( basePackages = { "com.processpuzzle.fitnesse.connect.testbed.application", "com.processpuzzle.fitnesse.connect.testbed.file", "com.processpuzzle.fitnesse.connect.testbed.service" } )
@EntityScan( basePackages = "com.processpuzzle.fitnesse.connect.testbed.domain" )
@EnableJpaRepositories( basePackages = "com.processpuzzle.fitnesse.connect.testbed.integration" )
@EnableConfigurationProperties( value = {StorageProperties.class, TestbedApplicationProperties.class} )
public class TestbedApplication {
   private static Server dbServer;
   private static Logger logger = LoggerFactory.getLogger( TestbedApplication.class );

   public static void main( String[] args ) throws SQLException {
      dbServer = Server.createTcpServer().start();
      SpringApplication.run( TestbedApplication.class, args );
   }

   @PreDestroy
   public void shutDown() {
      if( dbServer != null ){
         logger.info( "H2 database will be stopped" );
         dbServer.stop();
      }
   }
}
