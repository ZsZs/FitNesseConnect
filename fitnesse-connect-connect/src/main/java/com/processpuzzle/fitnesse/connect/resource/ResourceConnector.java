package com.processpuzzle.fitnesse.connect.resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class ResourceConnector {
   private static final Logger logger = LoggerFactory.getLogger( ResourceConnector.class );
   private ApplicationContext applicationContext;
   private final String resourcePath;

   // constructors
   public ResourceConnector( String resourcePath ) {
      this.resourcePath = resourcePath;
      applicationContext = IntegratedApplicationTester.getInstance().getApplicationContext();
      logCurrentWorkingDirectory();
   }

   // public accessors and mutators
   public List<Resource> retrieveResources() {
      Resource[] foundResources;
      try{
         foundResources = applicationContext.getResources( resourcePath );
         return Arrays.asList( foundResources );
      }catch( IOException e ){
         logger.error( "Failed to access resources at: " + resourcePath );;
      }

      return null;
   }

   public Resource retrieveResource() {
      Resource resource = applicationContext.getResource( resourcePath );
      if( resource.exists() ){
         return resource;
      }else {
         return null;
      }
   }

   // protected, private helper methods
   private void logCurrentWorkingDirectory() {
      try{
         String current = new java.io.File( "." ).getCanonicalPath();
         logger.info( "Current working directory: " + current );
      }catch( IOException e ){
         logger.error( "Failed to determine current working derectory.", e );
      }
   }

}
