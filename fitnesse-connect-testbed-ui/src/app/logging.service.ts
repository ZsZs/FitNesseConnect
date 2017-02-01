import { Injectable } from '@angular/core';

@Injectable()
export class LoggingService {

  writeToLog( logMessage: string ) {
    console.log( logMessage );
  }

}
