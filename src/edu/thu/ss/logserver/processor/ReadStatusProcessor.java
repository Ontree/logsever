package edu.thu.ss.logserver.processor;

import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.LogFileReader;

public class ReadStatusProcessor extends ReadProcessor {
	LogFileReader LogFile;
	public ReadStatusProcessor(Request request) {
		super(request);
		LogFile = super.LogFile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		
		LogItem logItem = new LogItem();
        while ((logItem=readLine())!=null) {
            
        }
		
	}
	
}
