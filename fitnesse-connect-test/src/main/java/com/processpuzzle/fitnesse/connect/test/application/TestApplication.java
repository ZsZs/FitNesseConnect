package com.processpuzzle.fitnesse.connect.test.application;

import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan( basePackages = { "com.processpuzzle.fitnesse.connect.test.application", "com.processpuzzle.fitnesse.connect.test.service" })
@EntityScan( basePackages = "com.processpuzzle.fitnesse.connect.test.domain" )
@EnableJpaRepositories( basePackages = "com.processpuzzle.fitnesse.connect.test.integration" )
public class TestApplication extends SpringBootServletInitializer {
   private static Server dbServer;
   private static Logger logger = LoggerFactory.getLogger( TestApplication.class );

   public static void main( String[] args ) throws SQLException {
      dbServer = Server.createTcpServer().start();
      SpringApplication.run( TestApplication.class, args );
   }
   
   @PreDestroy public void shutDown(){
      logger.info( "H2 database will be stopped" );
      dbServer.stop();
   }
}
