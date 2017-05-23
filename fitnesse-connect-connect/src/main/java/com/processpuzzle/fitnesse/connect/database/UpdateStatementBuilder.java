package com.processpuzzle.fitnesse.connect.database;

import java.text.MessageFormat;
import java.util.List;

import com.google.common.collect.Lists;

public class UpdateStatementBuilder extends SqlStatementBuilder {
   private static final String UPDATE_SQL_TEMPLATE = "UPDATE {0} SET {1} WHERE {2};";
   protected final String whereClause;

   // constructors
   public UpdateStatementBuilder( String tableName, String whereClause ) {
      super( tableName );
      this.whereClause = whereClause;
   }

   // public accessors and mutators
   public String buildUpdateSql( List<String> valueRow ) {
      String updateFragment = "";
      List<String> valueRowReturn = Lists.newArrayList();
      
      for( int i = 0; i < columnNames.size(); i++ ){
         String column = columnNames.get( i );
         if( !updateFragment.isEmpty() ) {
            updateFragment += ", ";
         }
         
         updateFragment += column + " = " + valueRow.get( i );
         valueRowReturn.add( "pass" );
      }
      
      tableReturn.add( valueRowReturn );
      return MessageFormat.format( UPDATE_SQL_TEMPLATE, new Object[] { tableName, updateFragment, this.whereClause } );
   }
}
