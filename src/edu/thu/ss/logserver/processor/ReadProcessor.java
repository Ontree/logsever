package edu.thu.ss.logserver.processor;

import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.LogFileReader;

public abstract class ReadProcessor implements Runnable{
	
	public ReadProcessor(Request request){
		LogFileReader LogFile = new LogFileReader();
		
	}
	

	public void readLine(){
		
	}
}
