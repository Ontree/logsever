package edu.thu.ss.logserver.processor;

import java.util.ArrayList;
import java.util.Iterator;

import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.LogFileReader;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class ReadStatusProcessor extends ReadProcessor {
	LogFileReader LogFile;
	Request request;
	public class Status{
		public String name;
		public String roomId;
		Status(String name, String roomId){
			this.name = name;
			this.roomId = roomId;
		}
	}
	
	public ReadStatusProcessor(Request request) {
		super(request);
		LogFile = super.logFile;
		this.request = request;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		ArrayList<Status> statusList = new ArrayList<Status>();
		Iterator<Status> iter; 
		LogItem logItem;
		Status statusItem;
		int flag;
		
		logFile.startRead();
		//read lines of logFile
        while ((logItem=readLine())!=null) { 
        	flag = 1;
        	iter = statusList.iterator();
        	//for each item of logFile, modify statusList
        	while(iter.hasNext()){  
        		statusItem = (Status)iter.next();
        		if (statusItem.name.equals(logItem.name)){
        			if (statusItem.roomId == null){
       					statusItem.roomId = logItem.roomId;
       				}else{
       					statusItem.roomId = null;
       				}
        			flag = 0;
       				break;
       			} 
        	}
        	if (flag == 1){
        		statusList.add(new Status(logItem.name, logItem.roomId));
        	}
        }
        logFile.endRead();
        Global.ThreadCount.decrementAndGet();
        
        String response = "";
        iter = statusList.iterator();
        if (iter.hasNext()){
	    	while(true){  
	    		statusItem = (Status)iter.next();
	    		if (statusItem.roomId != null){
	    			response = response + statusItem.roomId + "\t" + statusItem.name;
	    		}
	    		if(iter.hasNext()){
	    			if (statusItem.roomId != null){
	    				response = response + "\n";
	    			}
	    		}else{
	    			break;
	    		}
	    	}
        }
        Global.response(request.id, response);
		
	}
	
}
