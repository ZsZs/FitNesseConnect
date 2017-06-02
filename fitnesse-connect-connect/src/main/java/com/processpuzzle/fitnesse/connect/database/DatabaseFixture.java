package com.processpuzzle.fitnesse.connect.database;

import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration;

public abstract class DatabaseFixture<B extends SqlStatementBuilder> {
   protected ApplicationConfiguration applicationConfiguration;
   protected DatabaseConnector databaseClient;
   protected String databaseName;
   protected B sqlBuilder;
   
   // constructors
   
   public DatabaseFixture( ApplicationConfiguration serviceConfiguration, String databaseName ) {
      this.applicationConfiguration = serviceConfiguration;
      this.databaseName = databaseName;
      createDatabaseClient();
   }
   
   // protected, private helper methods
   protected void createDatabaseClient() {
      if( applicationConfiguration != null ) databaseClient = applicationConfiguration.createDatabaseClient( databaseName );
      else throw new UnknownApplicationConfigurationException( );
   }

}
