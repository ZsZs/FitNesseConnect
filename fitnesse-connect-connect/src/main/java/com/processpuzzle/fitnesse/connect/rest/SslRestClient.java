package com.processpuzzle.fitnesse.connect.rest;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class SslRestClient extends RestClient {
   private final String certificateName;

   // constructors
   public static SslRestClient create( String resourceURI, final String certificateName ) throws Exception {
      SslRestClient restClient = new SslRestClient( resourceURI, certificateName );

      return restClient;
   }

   protected SslRestClient( final String resourceURI, final String certificateName ) throws Exception {
      super( resourceURI );
      this.certificateName = certificateName;

      restTemplate.setRequestFactory( new HttpComponentsClientHttpRequestFactory( HttpClients.custom().setSSLSocketFactory( socketFactory() ).build() ) );
   }

   // public accessors and mutators

   // protected, private helper methods
   private SSLConnectionSocketFactory socketFactory() throws Exception {
      char[] password = "testclient3".toCharArray();
      KeyStore truststore = KeyStore.getInstance( "PKCS12" );
      truststore.load( getKeyStoreFile(), password );

      // Never ever use this in production!
      TrustStrategy trustStrategy = new TrustStrategy() {
         @Override public boolean isTrusted( java.security.cert.X509Certificate[] arg0, String arg1 ) throws java.security.cert.CertificateException {
            return true;
         }
      };

      SSLContextBuilder builder = new SSLContextBuilder();
      builder.loadKeyMaterial( truststore, password );
      builder.loadTrustMaterial( truststore, trustStrategy );

      return new SSLConnectionSocketFactory( builder.build(), new NoopHostnameVerifier() );
   }

   private InputStream getKeyStoreFile() throws IOException {
      ClassPathResource resource = new ClassPathResource( this.certificateName + ".p12" );
      return resource.getInputStream();
   }
}
