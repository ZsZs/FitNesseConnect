package com.processpuzzle.fitnesse.connect.resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;

import org.junit.Test;

public class DirectoryHasFileTest {
   private static final String FILE_PATH = "file:./src/test/fitnesse/files/sample/";
   private static final String FILE_NAME = "SampleOne.xlsx";

   @Test public void constructor_whenFileExists_successfullyInstantiatesFixture() throws FileNotFoundException{
      assertThat( new DirectoryHasFile( FILE_PATH + FILE_NAME ), notNullValue() );
   }
   
   @Test( expected = FileNotFoundException.class ) public void constructor_whenFileNotExists_throwsException() throws FileNotFoundException{
      new DirectoryHasFile( FILE_PATH + "unknown_file" );
   }
}
