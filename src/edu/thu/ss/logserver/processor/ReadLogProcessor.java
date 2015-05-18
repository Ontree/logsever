package edu.thu.ss.logserver.processor;


import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.request.ReadTimeRequest;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.WriteRequest;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class ReadLogProcessor extends ReadProcessor {
	WriteRequest request;
	public ReadLogProcessor(Request request) {
		super(request);
		this.request = (WriteRequest)request;
	}

	@Override
	public void run() {
		LogItem logItem;
        LogItem lastEmployeeItem = null;
		logFile.startRead();
		while ((logItem=readLine())!=null) {
			if (logItem.name.equals(request.name)){
				lastEmployeeItem = logItem;
			}
		}
		Global.lastLogItem = lastEmployeeItem;
		
		logFile.endRead();
		Global.ThreadCount.decrementAndGet();
	}
	
}
