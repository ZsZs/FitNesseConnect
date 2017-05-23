package com.processpuzzle.fitnesse.connect.database;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class UpdateStatementBuilderTest {
   
   private List<List<String>> table;
   @Before public void beforeEachTests(){
      List<String> headerRow = Lists.newArrayList( "column_one", "column_two" );
      List<String> valueRow = Lists.newArrayList( "'name1'", "'pass1'" );
      table = Lists.newArrayList( headerRow, valueRow );
      
   }
   
   @Test public void buildSql(){
      UpdateStatementBuilder updateBuilder = new UpdateStatementBuilder( "sample_table", "id = 0" );
      
      updateBuilder.analyseHeaderRow( table.get( 0 ) );
      String updateSql = updateBuilder.buildUpdateSql( table.get( 1 ) );
      
      assertThat( updateSql, equalTo( "UPDATE sample_table SET column_one = 'name1', column_two = 'pass1' WHERE id = 0;" ));
   }

}
