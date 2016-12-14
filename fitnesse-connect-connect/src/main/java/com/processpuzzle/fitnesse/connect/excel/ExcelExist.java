package com.processpuzzle.fitnesse.connect.excel;

import java.util.List;

import com.processpuzzle.fitnesse.connect.resource.DirectoryHasFile;

public class ExcelExist {
   private final DirectoryHasFile directoryHasFile;
   
   // constructors
   public ExcelExist( final String resourceUrl ){
      this.directoryHasFile = new DirectoryHasFile( resourceUrl ); 
   }
   
   // public accessors and mutators
   public List<List<List<String>>> query() {
      return directoryHasFile.query();
   }

   public void table( List<List<String>> table ) {
      this.directoryHasFile.table( table );
   }
   
   // proterties
   // @formatter:off
   public List<String> getExpectedResourceProperties() { return directoryHasFile.getExpectedResourceProperties(); }
   public String getResourcePath() { return directoryHasFile.getResourcePath(); }
   // @formatter:on

}
