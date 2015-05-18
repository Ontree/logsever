package edu.thu.ss.logserver.processor;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.request.ReadEmployeeRequest;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class ReadEmployeeProcessor extends ReadProcessor {
	String name;
	Request request;
	public ReadEmployeeProcessor(Request request) {
		super(request);
		this.request = request;
		name = ((ReadEmployeeRequest)request).name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		Set<String> roomSet = new LinkedHashSet<String>();
		Iterator<String> iter; 
		LogItem logItem;
		String roomId;
		
		logFile.startRead();
		//read lines of logFile
        while ((logItem=readLine())!=null) { 
        	if (!logItem.name.equals(name)){
        		continue;
        	}
        	if (logItem.type.equals(Global.Type.valueOf("Leave"))){
        		continue;
        	}
        	roomSet.add(logItem.roomId);
        }
        logFile.endRead();
        Global.ThreadCount.decrementAndGet();
        
        
        String response = "";
        iter = roomSet.iterator();
        
        if (iter.hasNext()){
	    	while(true){  
	    		roomId = (String)iter.next();
	    		response = response + (roomId);
	    		if (iter.hasNext()){
	    			response = response + ",";
	    		}else{
	    			break;
	    		}
	 
	    	}
        }
        
        Global.response(request.id, response);
		
	}
	
}
