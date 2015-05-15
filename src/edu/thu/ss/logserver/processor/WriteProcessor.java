package edu.thu.ss.logserver.processor;

import java.io.File;
import java.io.IOException;

import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.Global.Type;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.WriteRequest;
import edu.thu.ss.logserver.request.util.LogFileReader;
import edu.thu.ss.logserver.request.util.LogFileWriter;

public class WriteProcessor extends ReadProcessor{
	
	WriteRequest request;
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
		runWrite();
	}
	
	public void WriteLogItem(){
		logFileWriter.startWrite();
		String OutputContent = "";
		OutputContent += request.timestmp + ",";
		OutputContent += request.name + ",";
		OutputContent += request.roomId + ",";
		OutputContent += (request.type == Type.Enter)?"Enter":"Leave";
		logFileWriter.println(OutputContent);
		logFileWriter.endWrite();
	}
	
	public void runWrite(){
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
