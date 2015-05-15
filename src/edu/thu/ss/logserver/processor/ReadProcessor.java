package edu.thu.ss.logserver.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.thu.ss.logserver.Global;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.LogFileReader;

public abstract class ReadProcessor implements Runnable{
	LogFileReader logFile;
	public ReadProcessor(Request request){
		try{
			File file=new File(Global.LOG_FILE_PATH);	
			logFile = new LogFileReader(file);
		}catch(Exception e1){	
		}
		
	}
	

	public LogItem readLine(){
		String line="";
        String[] arrs=null;
        try {
			line=logFile.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        if (line != null){
        	LogItem logItem = new LogItem();
            arrs=line.split(",");
            logItem.timestmp = Long.parseLong(arrs[0]);
            logItem.name = arrs[1];
            logItem.roomId = arrs[2];
            logItem.type = Global.Type.valueOf(arrs[3]);
            return logItem;
        }else{
        	return null;
        }
	}
}
