package com.processpuzzle.fitnesse.connect.rest;

import java.util.concurrent.RecursiveTask;

public abstract class ConcurrentServiceAgent<T extends SessionResults<?>> extends RecursiveTask<T> {
   private static final long serialVersionUID = 6677219499083016662L;
   protected RestClient restClient;
   protected String sessionId;
   protected T results;
   protected long responseTime;

   // constructors
   public ConcurrentServiceAgent( RestClient restClient, T results ) {
      this( restClient, null, results );
   }
   
   public ConcurrentServiceAgent( RestClient restClient, String sessionId, T results ) {
      this.restClient = restClient;
      this.sessionId = sessionId;
      this.results = results;
   }
   
   // protected, private helper methods
   @Override protected T compute() {
      results.setSessionStarted( System.currentTimeMillis() );
      beforeService();
      doService();
      afterService();
      results.setSessionEnded( System.currentTimeMillis() );
      return results;
   }
   
   protected abstract void afterService();
   protected abstract void beforeService();
   protected abstract void doService();
}
