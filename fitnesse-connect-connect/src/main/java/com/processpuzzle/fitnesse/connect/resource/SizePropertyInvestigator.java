package com.processpuzzle.fitnesse.connect.resource;

import java.io.IOException;

import org.springframework.core.io.Resource;

public class SizePropertyInvestigator extends ResourcePropertyInvestigator {

   protected SizePropertyInvestigator( Resource subjectResource ) {
      super( subjectResource );
   }

   @Override public String determinePropertyValue() {
      try{
         return Long.toString( subjectResource.contentLength() );
      }catch( IOException e ){
         e.printStackTrace();
      }
      
      return null;
   }

}
