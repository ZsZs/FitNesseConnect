package com.processpuzzle.fitnesse.connect.database;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnector {
   private final JdbcTemplate jdbc;

   @Autowired public DatabaseConnector( JdbcTemplate jdbc ) {
      this.jdbc = jdbc;
   }

   public void delete( String sqlStatement ) {
      this.jdbc.execute( sqlStatement );
   }

   public void insert( String sql ){
      jdbc.execute( sql );
   }
   
   public List<Map<String, Object>> query( String sql ) {
      return jdbc.queryForList( sql );
   }

   // properties
   // @formatter:off
   public JdbcTemplate getJdbcTemplate() { return jdbc; }
   // @formatter:on
}
