package com.processpuzzle.fitnesse.connect.file;

import java.util.List;
import static java.util.Arrays.asList;

public class DirectoryHasFile {
   private final String directoryName;
   private final String fileName;

   // constructors
   public DirectoryHasFile( String directoryName, String fileName ) {
      this.directoryName = directoryName;
      this.fileName = fileName;
   }

   // public accessors and mutators
   public List<List<List<String>>> query() {
      return asList( asList( asList( "file", this.fileName )));
   }

   // properties
   // @formatter:off
   public String getDirectoryName() { return directoryName; }
   public String getFileName() { return fileName; }
   // @formatter:on
   
}
