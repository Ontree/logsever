package edu.thu.ss.logserver.processor;

import edu.thu.ss.logserver.Global.Type;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.WriteRequest;

public class WriteProcessor extends ReadProcessor{
	
	WriteRequest request;
	
	public WriteProcessor(Request request) {
		super(request);
		this.request = (WriteRequest)request;
	}
	
	public void WriteLogItem(){
		
	}
	
	@Override
	public void run(){
		LogItem logItem;
		LogItem lastEmployeeItem = null;
		logFile.startRead();
		while ((logItem=readLine())!=null) {
			if (logItem.name.equals(request.name)){
				lastEmployeeItem = logItem;
			}
		}
		if (lastEmployeeItem == null){
			if (request.type == Type.Enter)
				WriteLogItem();
			else
				System.out.printf("error: Illegal Leave");
			return;
		}
		if (lastEmployeeItem.timestmp >= request.timestmp){
			System.out.println("error: Illegal Timstamp");
			return;
		}
		if (lastEmployeeItem.type == Type.Enter 
				&& request.type == Type.Leave 
				&& lastEmployeeItem.roomId.equals(request.roomId)){
			WriteLogItem();
		}else if (lastEmployeeItem.type == Type.Leave 
				&& request.type == Type.Enter ){
			WriteLogItem();	
		}else{
			System.out.printf("error: Illegal Leave or Enter");
		}
	}
}
