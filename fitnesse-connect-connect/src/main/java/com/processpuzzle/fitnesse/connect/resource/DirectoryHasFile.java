package com.processpuzzle.fitnesse.connect.resource;

import static java.util.Arrays.asList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;

public class DirectoryHasFile {
   private List<String> expectedResourceProperties = Lists.newArrayList();
   private final String resourcePath;

   // constructors
   public DirectoryHasFile( String resourcePath ) {
      this.resourcePath = adjustResourcePath( resourcePath );
   }

   // public accessors and mutators
   public List<List<List<String>>> query() {
      List<List<List<String>>> rowList = Lists.newArrayList();

      List<Resource> foundResources = new ResourceConnector( resourcePath ).retrieveResources();
      for( Resource resource : foundResources ){
         List<List<String>> row = Lists.newArrayList();

         for( String resourceProperty : expectedResourceProperties ){
            String value = determineResourceProperty( resourceProperty, resource );
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

   // protected, private helper methods
   private String adjustResourcePath( String resourcePath ) {
      String adjustedPath = StringUtils.endsWith( resourcePath, "/" ) ? resourcePath : resourcePath + "/";
      adjustedPath = StringUtils.endsWith( adjustedPath, "*" ) ? adjustedPath : adjustedPath + "*";
      
      return adjustedPath;
   }
   
   private String determineResourceProperty( String resourceProperty, Resource subjectResource ) {
      return ResourcePropertyInvestigator.create( resourceProperty, subjectResource ).determinePropertyValue();
   }

   // properties
   // @formatter:off
   public String getResourcePath() {
      return resourcePath;
   }

   public List<String> getExpectedResourceProperties() {
      return expectedResourceProperties;
   }
   // @formatter:on

}
