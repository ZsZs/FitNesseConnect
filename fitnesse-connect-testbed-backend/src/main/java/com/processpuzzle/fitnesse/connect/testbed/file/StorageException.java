package com.processpuzzle.fitnesse.connect.testbed.file;

public class StorageException extends RuntimeException {
   private static final long serialVersionUID = -8343813814722816574L;

   // constructors
   public StorageException( String message ) {
      super( message );
   }

   public StorageException( String message, Throwable cause ) {
      super( message, cause );
   }
}
