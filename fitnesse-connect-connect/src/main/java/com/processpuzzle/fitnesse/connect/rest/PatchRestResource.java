package com.processpuzzle.fitnesse.connect.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class PatchRestResource extends RestConnector {
   private static final Logger logger = LoggerFactory.getLogger( PatchRestResource.class );

   public PatchRestResource( String configurationName, String resourcePath ) {
      super( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), resourcePath );
   }

   // public accessors and mutators
   public void execute() {
      logger.debug( "Executing PATCH " + this.resourcePath );
      patchResource();
   }

}
