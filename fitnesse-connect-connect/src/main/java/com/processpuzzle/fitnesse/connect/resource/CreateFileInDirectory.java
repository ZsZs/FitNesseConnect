package com.processpuzzle.fitnesse.connect.resource;

public class CreateFileInDirectory extends DirectoryConnector {

   public CreateFileInDirectory( String fileName, String resourcePath ) {
      super( resourcePath + fileName );
   }

}
