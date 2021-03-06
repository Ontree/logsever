package edu.thu.ss.logserver.processor;

import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.request.ReadIntervalRequest;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class ReadIntervalProcessor extends ReadProcessor {
	ReadIntervalRequest request;
	public ReadIntervalProcessor(Request request) {
		super(request);
		this.request = (ReadIntervalRequest)request;
		
	}

	@Override
	public void run() {
		
		LogItem logItem;
		String response = "";
		logFile.startRead();
		//read lines of logFile
        while ((logItem=readLine())!=null) { 
        	if (!(logItem.timestmp >= request.low && logItem.timestmp <=request.up)){
        		continue;
        	}
        	if (!response.equals("")){
        		response = response + "\n";
        	}
        	response = response + logItem.type.toString() + "\t" + 
        				logItem.name + "\t" + logItem.roomId; 
        }
        
        logFile.endRead();
        Global.ThreadCount.decrementAndGet();
       
        Global.response(request.id, response);
	}
	
}
