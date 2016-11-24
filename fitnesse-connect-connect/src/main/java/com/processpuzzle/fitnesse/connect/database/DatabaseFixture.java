package com.processpuzzle.fitnesse.connect.database;

import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;

public abstract class DatabaseFixture {
   protected ApplicationConfiguration applicationConfiguration;
   protected DatabaseConnector databaseClient;
   protected String databaseName;
   
   // constructors
   
   public DatabaseFixture( ApplicationConfiguration serviceConfiguration, String databaseName ) {
      this.applicationConfiguration = serviceConfiguration;
      this.databaseName = databaseName;
      createDatabaseClient();
   }
   
   // protected, private helper methods
   protected void createDatabaseClient() {
      databaseClient = applicationConfiguration.createDatabaseClient( databaseName );
   }

}
