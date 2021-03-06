package com.processpuzzle.fitnesse.connect.resource;

import static java.util.Arrays.asList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;

public class DirectoryHasFiles extends DirectoryConnector {
   private static final Logger logger = LoggerFactory.getLogger( DirectoryHasFiles.class );
   private List<String> expectedResourceProperties = Lists.newArrayList();

   // constructors
   public DirectoryHasFiles( String resourcePath ) {
      super( adjustResourcePath( resourcePath ) );
   }

   // public accessors and mutators
   public List<List<List<String>>> query() {
      List<List<List<String>>> rowList = Lists.newArrayList();

      List<Resource> foundResources = retrieveResources();
      for( Resource resource : foundResources ){
         List<List<String>> row = Lists.newArrayList();

         for( String resourceProperty : expectedResourceProperties ){
            String value = null;
            try{
               value = determineResourceProperty( resourceProperty, resource );
            }catch( UnknownResourcePropertyException e ){
               logger.error( e.getLocalizedMessage(), e );
               value = e.getLocalizedMessage();
            }
            row.add( asList( resourceProperty, value ) );
         }
         rowList.add( row );
      }

      return rowList;
   }

   public void table( List<List<String>> table ) {
      for( String cell : table.get( 0 ) ){
         expectedResourceProperties.add( cell );
      }
   }

   // properties
   // @formatter:off
   public String getResourcePath() { return resourcePath; }
   public List<String> getExpectedResourceProperties() { return expectedResourceProperties; }
   // @formatter:on

   // protected, private helper methods
   protected static String adjustResourcePath( String resourcePath ) {
      String adjustedPath = StringUtils.endsWith( resourcePath, "/" ) ? resourcePath : resourcePath + "/";
      adjustedPath = StringUtils.endsWith( adjustedPath, "*" ) ? adjustedPath : adjustedPath + "*";

      return adjustedPath;
   }

   private String determineResourceProperty( String resourceProperty, Resource subjectResource ) throws UnknownResourcePropertyException {
      return ResourcePropertyInvestigator.create( resourceProperty, subjectResource ).determinePropertyValue();
   }
}
