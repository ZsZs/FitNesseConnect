package com.processpuzzle.fitnesse.connect.resource;

import org.springframework.core.io.Resource;

public class FileNamePropertyInvestigator extends ResourcePropertyInvestigator {

   FileNamePropertyInvestigator( Resource subjectResource ) {
      super( subjectResource );
   }

   @Override public String determinePropertyValue() {
      return subjectResource.getFilename();
   }

}
