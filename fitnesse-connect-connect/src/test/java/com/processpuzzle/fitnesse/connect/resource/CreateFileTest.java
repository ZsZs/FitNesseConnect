package com.processpuzzle.fitnesse.connect.resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;

import org.junit.Ignore;
import org.junit.Test;

public class CreateFileTest {
   private static final String FILE_PATH = "file:./src/test/fitnesse/files/sample/";
   private static final String FILE_NAME = "SampleNew.txt";

   @Ignore @Test
   public void constructor_whenFileExists_successfullyInstantiatesFixture() throws FileNotFoundException {
      assertThat( new CreateFileInDirectory( FILE_NAME, FILE_PATH ), notNullValue() );
   }

   @Ignore @Test( expected = FileNotFoundException.class )
   public void constructor_whenDirectoryNotExists_throwsException() throws FileNotFoundException {
      new CreateFileInDirectory( FILE_NAME, "unknown_directory" );
   }

}
