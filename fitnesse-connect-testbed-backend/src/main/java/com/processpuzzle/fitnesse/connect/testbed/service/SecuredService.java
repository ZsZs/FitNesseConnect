package com.processpuzzle.fitnesse.connect.testbed.service;

import java.util.List;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.collect.Lists;
import com.processpuzzle.fitnesse.connect.testbed.application.RestApiController;
import com.processpuzzle.fitnesse.connect.testbed.domain.SecuredObject;

@RestApiController( "secure" )
@ExposesResourceFor( SecuredObject.class )
public class SecuredService {
   private List<SecuredObject> securedObjects = Lists.newArrayList();
   
   // constructors
   public SecuredService(){
      instantiateTestObjects();
   }
   
   @GetMapping( value = "", produces = MediaType.APPLICATION_JSON_VALUE ) public Iterable<SecuredObject> findAll() {
      return securedObjects;
   }

   // protected, private helper methods
   private void instantiateTestObjects() {
      this.securedObjects.add( new SecuredObject( "Testobject One" ));
      this.securedObjects.add( new SecuredObject( "Testobject Two" ));
   }
}
