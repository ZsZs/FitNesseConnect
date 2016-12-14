package com.processpuzzle.fitnesse.connect.resource;

import org.springframework.core.io.Resource;

public abstract class ResourcePropertyInvestigator {
   protected final Resource subjectResource;
   
   // constructors
   protected ResourcePropertyInvestigator( Resource subjectResource ){
      this.subjectResource = subjectResource;
   }
   
   // factory methods
   public static ResourcePropertyInvestigator create( final String property, final Resource subjectResource ) throws UnknownResourcePropertyException{
      if( relaxedPropetyMathcer( property ) == ResourceProperties.FILE_NAME ){
         return new FileNamePropertyInvestigator( subjectResource );
      } else if( relaxedPropetyMathcer( property ) == ResourceProperties.SIZE ){
         return new SizePropertyInvestigator( subjectResource );
      }else{
         throw new UnknownResourcePropertyException( property );
      }
   }
   
   // public accessors and mutators
   public abstract String determinePropertyValue();

   private static ResourceProperties relaxedPropetyMathcer( final String property ){
      if( property.equals( "file name" ) ) return ResourceProperties.FILE_NAME;
      else if( property.equals( "size" )) return ResourceProperties.SIZE;
      else return ResourceProperties.UNKNOWN;
   }
}
