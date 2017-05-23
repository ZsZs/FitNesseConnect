package com.processpuzzle.fitnesse.connect.database;

import java.util.List;

import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class UpdateDatabaseTable extends DatabaseFixture<UpdateStatementBuilder> {

   // constructors
   public UpdateDatabaseTable( String configurationName, String tableName, String whereClause ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), tableName, whereClause );
   }
   
   protected UpdateDatabaseTable( ApplicationConfiguration applicationConfiguration, String tableName, String whereClause ) {
      super( applicationConfiguration, null );
      this.sqlBuilder = new UpdateStatementBuilder( tableName, whereClause );
   }

   // public accessors and mutators
   public List<List<String>> doTable( List<List<String>> table ) {
      this.sqlBuilder.analyseHeaderRow( table.get( 0 ));

      databaseClient.update( this.sqlBuilder.buildUpdateSql( table.get( 1 ) ));
      return sqlBuilder.getTableReturn();
   }
}
