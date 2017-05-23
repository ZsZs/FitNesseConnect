package com.processpuzzle.fitnesse.connect.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class PostRestResource extends RestConnector {
   private static final Logger logger = LoggerFactory.getLogger( PostRestResource.class );

   public PostRestResource( String configurationName, String resourcePath ) {
      super( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), resourcePath );
   }

   // public accessors and mutators
   public void execute() {
      logger.debug( "Executing POST " + this.resourcePath );
      postResource();
   }
}
