package com.processpuzzle.fitnesse.connect.testbed.file;

public class StorageFileNotFoundException extends StorageException {
   private static final long serialVersionUID = -9115396949179291028L;

   // constructors
   public StorageFileNotFoundException( String message ) {
      super( message );
   }

   public StorageFileNotFoundException( String message, Throwable cause ) {
      super( message, cause );
   }
}