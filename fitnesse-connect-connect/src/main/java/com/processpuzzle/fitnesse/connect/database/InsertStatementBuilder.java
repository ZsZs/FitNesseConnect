package com.processpuzzle.fitnesse.connect.database;

import java.text.MessageFormat;
import java.util.List;

public class InsertStatementBuilder extends SqlStatementBuilder {
   private static final String INSERT_SQL_TEMPLATE = "insert into {0} ( {1} ) values( {2} );";
   protected String insertSql;

   // constructors
   public InsertStatementBuilder( String tableName ){
      super( tableName );
   }
   
   public String buildInsertSql( List<String> valueRow ) {
      return MessageFormat.format( INSERT_SQL_TEMPLATE, new Object[] { tableName, columnNameCSV(), columnValueCSV( valueRow ) } );
   }
}
