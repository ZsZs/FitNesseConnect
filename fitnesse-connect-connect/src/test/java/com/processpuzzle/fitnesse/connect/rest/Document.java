package com.processpuzzle.fitnesse.connect.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Document {
   private String name;
   private String description;

   public Document() {}

   // properties
   // @formatter:off
   public String getDescription() { return description; }
   public String getName() { return name; }
   public void setDescription(String description) { this.description = description; }
   public void setName(String name) { this.name = name; }
   // @formatter:on

}
