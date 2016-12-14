package com.processpuzzle.fitnesse.connect.resource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.springframework.core.io.Resource;

public class ResourcePropertyInvestigatorTest {
   @Test public void create_whenValidPropertyIsGiven_instantiatesProperInvestigator() throws UnknownResourcePropertyException{
      assertThat( ResourcePropertyInvestigator.create( ResourceProperties.FILE_NAME.getCannonicalName(), mock( Resource.class )), instanceOf( FileNamePropertyInvestigator.class ));
      assertThat( ResourcePropertyInvestigator.create( ResourceProperties.SIZE.getCannonicalName(), mock( Resource.class )), instanceOf( SizePropertyInvestigator.class ));
   }
   
   @Test( expected = UnknownResourcePropertyException.class )
   public void create_whenUnknownProperty_throwsException() throws UnknownResourcePropertyException{
      ResourcePropertyInvestigator.create( "unknown", mock( Resource.class ) );
   }
}
