package com.processpuzzle.fitnesse.connect.excel;

import java.util.List;

import com.google.common.collect.Lists;

public class ExcelExist {
   private final String resourceUrl;
   private List<String> columnNames = Lists.newArrayList();
   
   // constructors
   public ExcelExist( final String resourceUrl ){
      this.resourceUrl = resourceUrl;
   }
   
   // public accessors and mutators
   public List<List<List<String>>> query() {
      List<List<List<String>>> rowList = Lists.newArrayList();
      return rowList;
   }

   public void table( List<List<String>> table ) {
      for( List<String> row : table ){
         for( String cell : row ){
            columnNames.add( cell );
         }
      }
   }
   
   // proterties
   // @formatter:off
   public String getResourceUrl() { return resourceUrl; }
   // @formatter:on
}
