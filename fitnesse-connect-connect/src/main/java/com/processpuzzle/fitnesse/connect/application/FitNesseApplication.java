package com.processpuzzle.fitnesse.connect.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan( basePackages = "com.processpuzzle.fitnesse.connect" )
public class FitNesseApplication {
   public static void main( String[] args ) {
      SpringApplication.run( FitNesseApplication.class, args );
   }

}
