package com.processpuzzle.fitnesse.connect.database;

import com.processpuzzle.fitnesse.connect.application.IntegratedApplicationTester;

public class TestDatabaseFixture extends DatabaseFixture<SelectStatementBuilder> {

   public TestDatabaseFixture( String configurationName, String databaseName ) {
      super( IntegratedApplicationTester.getInstance().getConfiguration( configurationName ), databaseName );
   }

}
