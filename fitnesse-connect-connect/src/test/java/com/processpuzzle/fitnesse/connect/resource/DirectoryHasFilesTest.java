package com.processpuzzle.fitnesse.connect.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

@RunWith( SpringRunner.class )
public class DirectoryHasFilesTest {
   private static final String RELATIVE_DIRECTORY_PATH = "file:./src/test/fitnesse/files/sample";
   private DirectoryHasFiles directoryHasFile;
   private List<List<String>> table;

   @Before public void beforeEachTest(){
      directoryHasFile = new DirectoryHasFiles( RELATIVE_DIRECTORY_PATH );
      
      List<String> headerRow = Lists.newArrayList( "file name", "size" );
      List<String> file1 = Lists.newArrayList( "SampleOne.xlsx", "8783" );
      List<String> file2 = Lists.newArrayList( "SampleTwo.xlsx", "9044" );
      List<String> file3 = Lists.newArrayList( "testresource.txt", "txt" );
      table = Lists.newArrayList( headerRow, file1, file2, file3 );
      directoryHasFile.table( table );      
   }
   
   @Test public void table_determinesPropertiesToCheck(){
      // please note that to table() method is invoked in the setup
      assertThat( directoryHasFile.getExpectedResourceProperties().size(), equalTo( 2 ));
      assertThat( directoryHasFile.getExpectedResourceProperties(), hasItem( "file name" ));
      assertThat( directoryHasFile.getExpectedResourceProperties(), hasItem( "size" ));
   }

   @Test public void query_whenIsFileSystem_returnsFileProperties(){
      List<List<List<String>>> returnTable = directoryHasFile.query();
      
      assertThat( returnTable.size(), equalTo( 2 ));
      assertThat( returnTable.get( 0 ).get( 0 ), hasItems( "file name", "SampleOne.xlsx" ));
      assertThat( returnTable.get( 0 ).get( 1 ), hasItems( "size", "8783" ));
      assertThat( returnTable.get( 1 ).get( 0 ), hasItems( "file name", "SampleTwo.xlsx" ));
      assertThat( returnTable.get( 1 ).get( 1 ), hasItems( "size", "9044" ));
   }
}
