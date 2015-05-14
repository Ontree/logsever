package edu.thu.ss.logserver.processor;

import java.util.ArrayList;
import java.util.Iterator;

import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.LogFileReader;

public class ReadStatusProcessor extends ReadProcessor {
	LogFileReader LogFile;
	
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
		LogFile = super.LogFile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		ArrayList<Status> statusList = new ArrayList<Status>();
		Iterator<Status> iter; 
		LogItem logItem;
		Status statusItem;
		int flag;
		
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
        
        iter = statusList.iterator();
    	while(iter.hasNext()){  
    		statusItem = (Status)iter.next();
    		if (statusItem.roomId != null){
    			System.out.printf("%s\t%s\n", statusItem.roomId, statusItem.name);
    		}
    	}
		
	}
	
}
