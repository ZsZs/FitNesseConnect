package com.processpuzzle.fitnesse.connect.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class IntegratedTestConfiguration implements ApplicationContextAware{
   private static ApplicationContext applicationContext;

   // properties
   // @formatter:off
   public static <T> T getBean( Class<T> requiredType ){ return applicationContext.getBean( requiredType ); }
   @Override public void setApplicationContext( ApplicationContext context ) throws BeansException { applicationContext = context; }
   // @formatter:on
}
