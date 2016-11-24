package com.processpuzzle.fitnesse.connect.rest;

public class SessionResults<R> {
   protected Exception exception;
   protected String sessionId;
   protected long sessionEnded;
   protected long sessionStarted;
   protected R response;
   protected long responseTime;
   protected String userName;
   
   // properties
   // @formatter:off
   public Exception getException() { return exception; }
   public R getResponse() { return response; }
   public long getResponseTime() { return sessionEnded - sessionStarted; }
   public long getSessionEnded() { return sessionEnded; }
   public String getSessionId() { return sessionId; }
   public long getSessionStarted() { return sessionStarted; }
   public String getUserName() { return userName; }
   public void setException( Exception exception ) { this.exception = exception; }
   public void setResponse( R response ) { this.response = response; }
   public void setResponseTime( long responseTime ) { this.responseTime = responseTime; }
   public void setSessionEnded( long sessionEnded ) { this.sessionEnded = sessionEnded; }
   public void setSessionId( String sessionId ) { this.sessionId = sessionId; }
   public void setSessionStarted( long sessionStarted ) { this.sessionStarted = sessionStarted; }
   public void setUserName( String userName ) { this.userName = userName; }
   // @formatter:on
}
