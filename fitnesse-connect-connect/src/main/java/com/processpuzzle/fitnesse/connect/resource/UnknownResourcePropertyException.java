package com.processpuzzle.fitnesse.connect.resource;

import java.text.MessageFormat;

public class UnknownResourcePropertyException extends Exception {
   private static final long serialVersionUID = 4588112081630992830L;
   private static final String messageTemplate = "Resource property {0} is unknown. Valid properties are: 'file name', 'size'";

   public UnknownResourcePropertyException( String resourceProperty ){
      super( MessageFormat.format( messageTemplate, new Object[] { resourceProperty }));
   }
}
