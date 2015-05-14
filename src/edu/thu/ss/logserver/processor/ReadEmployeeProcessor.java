package edu.thu.ss.logserver.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.thu.ss.logserver.processor.ReadStatusProcessor.Status;
import edu.thu.ss.logserver.request.ReadEmployeeRequest;
import edu.thu.ss.logserver.request.Request;

public class ReadEmployeeProcessor extends ReadProcessor {
	String name;
	public ReadEmployeeProcessor(Request request) {
		super(request);
		name = ((ReadEmployeeRequest)request).name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		Set<String> roomSet = new LinkedHashSet<String>();
		Iterator<String> iter; 
		LogItem logItem;
		String roomId;
		int flag;
		
		//read lines of logFile
        while ((logItem=readLine())!=null) { 
        	if (!logItem.name.equals(name)){
        		continue;
        	}
        	if (logItem.type.equals(LogItem.Type.valueOf("Leave"))){
        		continue;
        	}
        	roomSet.add(logItem.roomId);
        }
        
        iter = roomSet.iterator();
    	while(true){  
    		roomId = (String)iter.next();
    		System.out.printf(roomId);
    		if (iter.hasNext()){
    			System.out.printf(",");
    		}else{
    			System.out.printf("\n");
    			break;
    		}
 
    	}
		
	}
	
}
