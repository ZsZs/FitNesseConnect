package com.processpuzzle.fitnesse.connect.application;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;

import com.google.common.collect.Maps;
import com.processpuzzle.fitnesse.connect.application.ApplicationConfiguration.DataSourceConfiguration;

@SpringBootApplication
@ComponentScan( basePackages = {"com.processpuzzle.fitnesse.connect.application", "com.processpuzzle.fitnesse.connect.database", "com.processpuzzle.fitnesse.connect.cockpit" })
@EnableConfigurationProperties
public class IntegratedApplicationTester implements ApplicationContextAware{
   private static ApplicationContext applicationContext;
   private static Map<String, ApplicationConfiguration> configurations = Maps.newHashMap();
   private ApplicationConfiguration currentConfiguration;
   private static IntegratedApplicationTester soleInstance;

   // constructors
   public IntegratedApplicationTester(){}
   
   public static IntegratedApplicationTester getInstance() { 
      if( applicationContext == null ){
         String[] args = {};
         main( args );
      }else if( soleInstance == null ){
         soleInstance = applicationContext.getBean( IntegratedApplicationTester.class );
      }
      return soleInstance; 
   }
   
   // public accessors and mutators
   public void addConfiguration( final String configurationName, final ApplicationConfiguration configuration ){
      configurations.put( configurationName, configuration );
   }
   
   public void configure( final String configurationName ){
      currentConfiguration = configurations.get( configurationName );
      if( currentConfiguration == null ){
         currentConfiguration = new ApplicationConfiguration();
         currentConfiguration.setDataSourceConfiguration( new DataSourceConfiguration() );
         configurations.put( configurationName, currentConfiguration );
      }
   }
   
   public static void main( String[] args ) {
      instantiateApplicationContext( args );
      if( soleInstance == null ) soleInstance = applicationContext.getBean( IntegratedApplicationTester.class );
   }

   public void initialize( String activeProfile ) {
      instantiateApplicationContext( new String[]{ "--spring.profiles.active=" + activeProfile } );
      if( soleInstance == null ) soleInstance = applicationContext.getBean( IntegratedApplicationTester.class );
   }

   // properties
   // @formatter:off
   public ApplicationContext getApplicationContext(){ return applicationContext; }
   public static <T> T getBean( Class<T> requiredType ){ return applicationContext.getBean( requiredType ); }
   public ApplicationConfiguration getConfiguration( String configurationName ) { return configurations.get( configurationName ); }
   public ApplicationConfiguration getCurrentConfiguration() { return currentConfiguration; }
   @Override public void setApplicationContext( ApplicationContext context ) throws BeansException {  applicationContext = context; }
   public void setContextPath( String contextPath ){ currentConfiguration.setContextRoot( contextPath ); }
   public void setDataDriverClassName( String dataDriverClassName ){ currentConfiguration.setDataDriverClassName( dataDriverClassName ); }
   public void setDataPassword( String dataPassword ){ currentConfiguration.setDataPassword( dataPassword ); }
   public void setDataUrl( String dataUrl ){ currentConfiguration.setDataUrl( dataUrl ); }
   public void setDataUserName( String dataUserName ){ currentConfiguration.setDataUserName( dataUserName ); }
   public void setHost( String host ){ currentConfiguration.setHost( host ); }
   public void setPort( String port ){ currentConfiguration.setPort( port ); }
   public void setProtocol( String protocol ){ currentConfiguration.setProtocol( protocol ); }
   // @formatter:on
   
   // protected, private helper methods
   private static void instantiateApplicationContext( String[] args ) {
      applicationContext = new SpringApplicationBuilder( IntegratedApplicationTester.class ).web( false ).run( args );
   }
}
