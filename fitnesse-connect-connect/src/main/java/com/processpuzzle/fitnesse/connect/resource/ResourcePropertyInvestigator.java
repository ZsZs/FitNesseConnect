package com.processpuzzle.fitnesse.connect.resource;

import org.springframework.core.io.Resource;

public abstract class ResourcePropertyInvestigator {
   protected final Resource subjectResource;
   
   // constructors
   protected ResourcePropertyInvestigator( Resource subjectResource ){
      this.subjectResource = subjectResource;
   }
   
   // factory methods
   public static ResourcePropertyInvestigator create( final String property, final Resource subjectResource ){
      if( relaxedPropetyMathcer( property ) == ResourcePropeties.FILE_NAME ){
         return new FileNamePropertyInvestigator( subjectResource );
      } else if( relaxedPropetyMathcer( property ) == ResourcePropeties.SIZE ){
         return new SizePropertyInvestigator( subjectResource );
      }
      
      return null;
   }
   
   // public accessors and mutators
   public abstract String determinePropertyValue();

   private static ResourcePropeties relaxedPropetyMathcer( final String property ){
      if( property.equals( "file name" ) ) return ResourcePropeties.FILE_NAME;
      else if( property.equals( "size" )) return ResourcePropeties.SIZE;
      else return ResourcePropeties.UNKNOWN;
   }
}
