package com.processpuzzle.fitnesse.connect.test.application;

import java.util.List;

import javax.xml.transform.Source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class TestApplicationConfiguration extends WebMvcConfigurerAdapter {
   private static final String SPRING_HATEOAS_OBJECT_MAPPER = "_halObjectMapper";
   @Autowired @Qualifier( SPRING_HATEOAS_OBJECT_MAPPER ) private ObjectMapper springHateoasObjectMapper;
   @Autowired private Jackson2ObjectMapperBuilder springBootObjectMapperBuilder;

   @Override public void addViewControllers( ViewControllerRegistry registry ) {
      registry.addViewController( "/files" ).setViewName( "uploadForm" );
   }

   @Override public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
      StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
      stringConverter.setWriteAcceptCharset( false );

      converters.add( new ByteArrayHttpMessageConverter() );
      converters.add( stringConverter );
      converters.add( new ResourceHttpMessageConverter() );
      converters.add( new SourceHttpMessageConverter<Source>() );
      converters.add( new AllEncompassingFormHttpMessageConverter() );
      converters.add( jackson2Converter() );
   }

   @Bean public MappingJackson2HttpMessageConverter jackson2Converter() {
      MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
      converter.setObjectMapper( objectMapper() );
      return converter;
   }

   @Bean( name = "objectMapper" ) @Primary ObjectMapper objectMapper() {
      springHateoasObjectMapper.configure( SerializationFeature.INDENT_OUTPUT, true );
      this.springBootObjectMapperBuilder.configure( this.springHateoasObjectMapper );

      return springHateoasObjectMapper;
   }
}
