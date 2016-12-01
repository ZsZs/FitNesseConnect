package com.processpuzzle.fitnesse.connect.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { IntegratedApplicationTester.class, ConnectorApplicationConfiguration.class, BasicHttpsSecurityApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT )
@ComponentScan( basePackages = {"com.processpuzzle.fitnesse.connect.database"} )
@ActiveProfiles( "unit-test" )
public class BasicHttpsSecurityApplicationTests {
   private SSLContext defaultContext;
   @LocalServerPort private int port;
   @Autowired private TestRestTemplate restTemplate;

   @Before public void setUp() throws Exception {
      this.defaultContext = SSLContext.getDefault();
   }

   @After public void reset() throws Exception {
      SSLContext.setDefault( this.defaultContext );
   }

   @Test( expected = ResourceAccessException.class ) public void testUnauthenticatedHello() throws Exception {
      ResponseEntity<String> httpsEntity = this.restTemplate.getForEntity( "/hi", String.class );
      assertThat( httpsEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
      assertThat( httpsEntity.getBody() ).containsIgnoringCase( "hello, world" );
   }

   @Test public void testAuthenticatedHello() throws Exception {
      TestRestTemplate restTemplate = new TestRestTemplate();
      HttpClientBuilder clientBuilder = HttpClients.custom().setSSLSocketFactory( socketFactory() );
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory( clientBuilder.build() );
      restTemplate.getRestTemplate().setRequestFactory( requestFactory );
      
      ResponseEntity<String> httpsEntity = restTemplate.getForEntity( "https://localhost:" + this.port + "/hi", String.class );
      assertThat( httpsEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
      assertThat( httpsEntity.getBody() ).containsIgnoringCase( "hello, world" );
   }

   // protected, private helper methods
   private SSLConnectionSocketFactory socketFactory() throws Exception {
      char[] password = "password".toCharArray();
      KeyStore truststore = KeyStore.getInstance( "PKCS12" );
      truststore.load( getKeyStoreFile(), password );
      SSLContextBuilder builder = new SSLContextBuilder();
      builder.loadKeyMaterial( truststore, password );
      builder.loadTrustMaterial( truststore, new TrustSelfSignedStrategy() );
      return new SSLConnectionSocketFactory( builder.build(), new NoopHostnameVerifier() );
   }

   private InputStream getKeyStoreFile() throws IOException {
      ClassPathResource resource = new ClassPathResource( "rod.p12" );
      return resource.getInputStream();
   }

}
