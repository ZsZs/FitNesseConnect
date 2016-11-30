package com.processpuzzle.fitnesse.connect.application;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
   @Autowired private static ApplicationContext applicationContext;
   private Map<String, ApplicationConfiguration> configurations = Maps.newHashMap();
   @Autowired private ApplicationConfiguration currentConfiguration;
   private static IntegratedApplicationTester soleInstance;

   // constructors
   public IntegratedApplicationTester(){
      soleInstance = this;
   }
   
   // public accessors and mutators
   public void addConfiguration( final String configurationName, final ApplicationConfiguration configuration ){
      configurations.put( configurationName, currentConfiguration );
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
      applicationContext = SpringApplication.run( IntegratedApplicationTester.class, args );
      soleInstance = getBean( IntegratedApplicationTester.class );
   }

   public void initialize( String activeProfile ) {
      applicationContext = SpringApplication.run( IntegratedApplicationTester.class, new String[]{ "--spring.profiles.active=" + activeProfile } );
      soleInstance = getBean( IntegratedApplicationTester.class );
      soleInstance.configurations.put( "connector", soleInstance.currentConfiguration );
   }

   @PostConstruct public void postConstruct(){
      configurations.put( "connector", currentConfiguration );
   }
   
   // properties
   // @formatter:off
   public static <T> T getBean( Class<T> requiredType ){ return applicationContext.getBean( requiredType ); }
   public ApplicationConfiguration getConfiguration( String configurationName ) { return configurations.get( configurationName ); }
   public ApplicationConfiguration getCurrentConfiguration() { return currentConfiguration; }
   public static IntegratedApplicationTester getInstance() { return soleInstance; }
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
}
