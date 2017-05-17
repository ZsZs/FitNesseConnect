package com.processpuzzle.fitnesse.connect.rest;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SslRestClient extends RestClient {
   private String certificateName;

   // constructors
   protected SslRestClient( ) throws Exception {
      super();
      restTemplate.setRequestFactory( new HttpComponentsClientHttpRequestFactory( HttpClients.custom().setSSLSocketFactory( socketFactory() ).build() ) );
   }

   // public accessors and mutators

   // properties
   // @formatter:off
   public String getCertificateName() { return this.certificateName; }
   public void setCertificateName( String certificateName ) { this.certificateName = certificateName; }
   // @formatter:on
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
