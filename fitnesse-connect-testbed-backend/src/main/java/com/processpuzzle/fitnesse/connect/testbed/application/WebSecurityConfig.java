package com.processpuzzle.fitnesse.connect.testbed.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired private AuthenticationEntryPoint authEntryPoint;
   @Autowired private TestbedApplicationProperties testbedConfiguration;

   @Override protected void configure( HttpSecurity http ) throws Exception {
      // @formatter:off
      http.csrf().disable()
          .authorizeRequests()
          .antMatchers( "/", "/home", "/api/cars/**", "/api/files/**" ).permitAll().anyRequest().authenticated()
          .and()
             .formLogin().loginPage( "/login" ).permitAll()
          .and()
             .httpBasic().authenticationEntryPoint( authEntryPoint )
          .and()
             .logout().permitAll();
      // @formatter:on
   }

   @Bean public UserDetailsService userDetailsService() {
      InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
      testbedConfiguration.getUsers().forEach( ( user, password ) -> {
         manager.createUser( User.withUsername( user ).password( password ).roles( "USER" ).build() );
      });
      
      return manager;
   }
}
