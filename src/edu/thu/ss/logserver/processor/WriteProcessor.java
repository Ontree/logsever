package edu.thu.ss.logserver.processor;

import java.io.File;
import java.io.IOException;

import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.Global.Type;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.WriteRequest;
import edu.thu.ss.logserver.request.util.LogFileWriter;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class WriteProcessor extends ReadProcessor{
	
	WriteRequest request;
	String response = "";
	LogFileWriter logFileWriter;
	
	public WriteProcessor(Request request) {
		super(request);
		this.request = (WriteRequest)request;
		File file = new File(Global.LOG_FILE_PATH);
		try{
			logFileWriter = new LogFileWriter(file);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void WriteLogItem(){
		logFileWriter.startWrite();
		String OutputContent = "";
		OutputContent += request.timestmp + ",";
		OutputContent += request.name + ",";
		OutputContent += request.roomId + ",";
		OutputContent += (request.type == Type.Enter)?"enter":"leave";
		logFileWriter.println(OutputContent);
		logFileWriter.flush();
        Global.response(request.id, "write successfully");
        Global.blockLock.unlock();
		logFileWriter.endWrite();
		logFileWriter.close();
	}
	
	public void run(){
		LogItem lastEmployeeItem = Global.lastLogItem;
		if (lastEmployeeItem == null){
			if (request.type == Type.Enter)
				WriteLogItem();
			else{
				Global.response(request.id, "error");
		        Global.blockLock.unlock();
			}
			return;
		}
		
		if (lastEmployeeItem.timestmp >= request.timestmp){
	        Global.response(request.id, "error");
	        Global.blockLock.unlock();
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
			Global.response(request.id, "error");
	        Global.blockLock.unlock();
			return;
		}
	}
}
