package edu.thu.ss.logserver.processor;


import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.request.ReadTimeRequest;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class ReadTimeProcessor extends ReadProcessor {
	ReadTimeRequest request;
	public ReadTimeProcessor(Request request) {
		super(request);
		this.request = (ReadTimeRequest)request;
	}

	@Override
	public void run() {
		long enterTime = -1, leaveTime, totalTime = 0;
		LogItem logItem;
		
		logFile.startRead();
		//read lines of logFile
        while ((logItem=readLine())!=null) { 
        	if (logItem.name.equals(request.name)
        			&&logItem.roomId.equals(request.roomId)){
        		if (logItem.type.equals(Global.Type.valueOf("Leave"))){
        			if (enterTime == -1){
        				System.out.printf("error:enterTime == -1\n");
        			}else{
        				leaveTime = logItem.timestmp;
        				if (enterTime > leaveTime){
        					System.out.printf("error:enterTime > leaveTime\n");
        				}
        				totalTime += leaveTime - enterTime;
        				enterTime = -1;
        			}
        		}else{ //enter
        			enterTime = logItem.timestmp; 
        		}
        	}
        }
        logFile.endRead();
        Global.ThreadCount.decrementAndGet();
        
        String response = "" + totalTime;
        Global.outputLock.lock();
        ResponseUtil.response(request.id, response);
        Global.outputLock.unlock();
    	
    		
		
	}
	
}
