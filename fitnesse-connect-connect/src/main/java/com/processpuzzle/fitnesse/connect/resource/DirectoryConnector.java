package com.processpuzzle.fitnesse.connect.resource;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.Resource;

public abstract class DirectoryConnector {
   protected final String resourcePath;
   
   // constructors
   public DirectoryConnector( final String resourcePath ){
      this.resourcePath = resourcePath;
   }
   
   // protected, private helper methods
   protected Resource retrieveResource() throws FileNotFoundException {
      Resource foundResource = new ResourceConnector( resourcePath ).retrieveResource();
      return foundResource;
   }

   protected List<Resource> retrieveResources() {
      List<Resource> foundResources = new ResourceConnector( resourcePath ).retrieveResources();
      return foundResources;
   }
}
