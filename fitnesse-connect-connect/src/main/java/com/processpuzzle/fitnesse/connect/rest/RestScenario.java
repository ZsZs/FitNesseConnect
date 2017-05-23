package com.processpuzzle.fitnesse.connect.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class RestScenario extends RestConnector {
   static final Logger logger = LoggerFactory.getLogger( RestScenario.class );
   // constructors
   public RestScenario( String configurationName, String resourcePath ) {
      super( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), resourcePath );
   }
   
   // public accessors and mutators
   public void getResource(  String resourceUri, String notUsedComment ) {
      getResource( resourceUri );
   }
}
