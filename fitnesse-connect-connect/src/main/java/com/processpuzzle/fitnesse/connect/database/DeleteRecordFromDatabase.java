package com.processpuzzle.fitnesse.connect.database;

import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;
import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class DeleteRecordFromDatabase extends DatabaseFixture {
   protected final String sqlStatement;
   
   // constructors
   public DeleteRecordFromDatabase( String configurationName, String sqlStatement ) {
      this( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), sqlStatement );
   }
   
   private DeleteRecordFromDatabase( ApplicationConfiguration serviceConfiguration, String sqlStatement ) {
      this( serviceConfiguration, null, sqlStatement );
   }

   private DeleteRecordFromDatabase( ApplicationConfiguration serviceConfiguration, String databaseName, String sqlStatement ) {
      super( serviceConfiguration, databaseName );
      this.sqlStatement = sqlStatement;
   }
   
   // public accesors and mutators
   public boolean delete(){
      databaseClient.delete( sqlStatement );
      return true;
   }
}
