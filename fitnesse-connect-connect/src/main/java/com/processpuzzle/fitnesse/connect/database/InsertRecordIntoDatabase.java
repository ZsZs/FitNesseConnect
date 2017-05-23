package com.processpuzzle.fitnesse.connect.database;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class InsertRecordIntoDatabase extends DatabaseFixture<InsertStatementBuilder> {

   // constructors
   public InsertRecordIntoDatabase( String configurationName, String tableName ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), tableName );
   }
   
   protected InsertRecordIntoDatabase( ApplicationConfiguration applicationConfiguration, String tableName ) {
      this( applicationConfiguration, null, tableName );
   }

   protected InsertRecordIntoDatabase( ApplicationConfiguration applicationConfiguration, String databaseName, String tableName ) {
      super( applicationConfiguration, databaseName );
      this.sqlBuilder = new InsertStatementBuilder( tableName );
   }

   // public accessors and mutators
   public List<List<String>> doTable( List<List<String>> table ) {
      this.sqlBuilder.analyseHeaderRow( table.get( 0 ));
      
      for( int i = 1; i < table.size(); i++ ){
         List<String> valueRow = table.get( i );
         Long primaryKey = null;

         if( !this.sqlBuilder.hasIdColumn() ){
            databaseClient.insert( sqlBuilder.buildInsertSql( valueRow ) );
         }else{
            Map<String, Object> columnValueMap = sqlBuilder.buildColumnValueMap( valueRow );
            SimpleJdbcInsert insert = new SimpleJdbcInsert( databaseClient.getJdbcTemplate() );
            insert.withTableName( sqlBuilder.getTableName() ).usingGeneratedKeyColumns( sqlBuilder.getIdColumn() );
            final Number key = insert.executeAndReturnKey( columnValueMap );
            primaryKey = key.longValue();
         }

         sqlBuilder.buildValueRowReturn( valueRow, primaryKey );
      }

      return sqlBuilder.getTableReturn();
   }

   // protected, private helper methods
}
