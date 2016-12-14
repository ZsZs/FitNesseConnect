package com.processpuzzle.fitnesse.connect.resource;

import java.io.FileNotFoundException;

public class DirectoryHasFile extends DirectoryConnector{

   // constructors
   public DirectoryHasFile( final String resourcePath ) throws FileNotFoundException{
     super( resourcePath );
     retrieveResource();
   }
}
