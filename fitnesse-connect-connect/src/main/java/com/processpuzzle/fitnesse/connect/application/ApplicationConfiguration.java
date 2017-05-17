package com.processpuzzle.fitnesse.connect.application;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.processpuzzle.fitnesse.connect.database.DatabaseConnector;
import com.processpuzzle.fitnesse.connect.rest.RestClient;
import com.processpuzzle.fitnesse.connect.rest.SslRestClient;

@ConfigurationProperties()
public class ApplicationConfiguration {
   protected String contextRoot;
   protected String host;
   protected String hostUrl;
   protected String port;
   protected String protocol = "http";
   @NestedConfigurationProperty protected DataSourceConfiguration dataSourceConfiguration;
   @Autowired private RestClient restClient;
   @Autowired private SslRestClient sslRestClient;

   // public accessors and mutators
   public String buildHostUrl() {
      String hostUrl = protocol + "://";
      hostUrl += host;
      hostUrl += (port != null && !port.isEmpty()) ? ":" + port : "";
      hostUrl += (contextRoot != null && !contextRoot.isEmpty()) ? "/" + contextRoot : "";
      
      return hostUrl; 
   }
   
   public DatabaseConnector createDatabaseClient() {
      return createDatabaseClient( null );
   }
   
   public DatabaseConnector createDatabaseClient( String databaseName ) {
      return new DatabaseConnector( this.createJdbcTemplate( databaseName ));
   }
   
   public JdbcTemplate createJdbcTemplate() {
      return createJdbcTemplate( null );
   }
   
   public JdbcTemplate createJdbcTemplate( String databaseName ) {
      DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
      dataSourceBuilder.url( determineDataUrl( databaseName ));
      dataSourceBuilder.username( dataSourceConfiguration.getUsername() );
      dataSourceBuilder.password( dataSourceConfiguration.getPassword() );
      DataSource dataSource = dataSourceBuilder.build(); 
      return new JdbcTemplate( dataSource );
   }
   
   public RestClient createRestClient( final String resourceURI ) {
      return restClient;
   }
   
   public SslRestClient createSslRestClient( final String resourceURI, final String certificateName ) throws Exception {
      return sslRestClient;
   }
   
   
   // properties
   // @formatter:off
   public String getContextRoot() { return contextRoot; }
   public String getHost() { return host; }
   public String getPort() { return port; }
   public String getProtocol() { return protocol; }
   public DataSourceConfiguration getDataSourceConfiguration() { return dataSourceConfiguration; }
   public String getDatasourceUser() { return dataSourceConfiguration.getUsername(); }
   public String getDatasourcePassword() { return dataSourceConfiguration.getPassword(); }
   public String getDatasourceDriverClass() { return dataSourceConfiguration.getDriverClassName(); }
   public String getDatasourceUrl() { return dataSourceConfiguration.getUrl(); }
   public void setContextRoot( String contextRoot ) { this.contextRoot = contextRoot; }
   public void setHost( String host ) { this.host = host; }
   public void setPort( String port ) { this.port = port; }
   public void setProtocol( String protocol ) { this.protocol = protocol; }
   public void setDataDriverClassName( String dataDriverClassName ) { this.dataSourceConfiguration.setDriverClassName( dataDriverClassName ); }
   public void setDataPassword( String dataPassword ) { this.dataSourceConfiguration.setPassword( dataPassword ); }
   public void setDataSourceConfiguration( DataSourceConfiguration dataSourceConfiguration ) { this.dataSourceConfiguration = dataSourceConfiguration; }
   public void setDataUrl( String dataUrl ) { this.dataSourceConfiguration.setUrl( dataUrl ); }
   public void setDataUserName( String dataUserName ) { this.dataSourceConfiguration.setUsername( dataUserName ); }
   // @formatter:on

   @Component
   public static class DataSourceConfiguration {
      protected String driverClassName;
      protected String url;
      protected String username;
      protected String password;

      // properties
      // @formatter:off
      public String getDriverClassName() { return driverClassName; }
      public String getPassword() { return password; }
      public String getUrl() { return url; }
      public String getUsername() { return username; }
      public void setUsername( String username ) { this.username = username; }
      public void setDriverClassName( String driverClassName ) { this.driverClassName = driverClassName; }
      public void setPassword( String password ) { this.password = password; }
      public void setUrl( String url ) { this.url = url; }
      // formatter:on
   }
   
   // protected, private helper methods
   private String determineDataUrl( String databaseName ){
      String dataUrl = "";
      if( databaseName != null && !databaseName.isEmpty() ){
         dataUrl = StringUtils.substringBeforeLast( dataSourceConfiguration.getUrl(), ";" ) + ";DatabaseName=" + databaseName ;
      }else {
         dataUrl = dataSourceConfiguration.getUrl();
      }
      return dataUrl;
   }
   
   protected String fullResourceUrl( String resourceURI ) {
      String fullResourceUrl;
      
      if( hostUrl != null ){
         fullResourceUrl = hostUrl + "/" + resourceURI;
      }else{
         fullResourceUrl = buildHostUrl() + "/" + resourceURI;
      }
      
      return fullResourceUrl;
   }
   
}
