package edu.thu.ss.logserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.thu.ss.logserver.request.util.ResponseUtil;

public class Global {
	
	public static enum Type {
		Enter, Leave;
	}
	public final static void response(int requestId, String response){
		Global.outputLock.lock();
        ResponseUtil.response(requestId, response);
        Global.outputLock.unlock();
	}
	public final static String LOG_FILE_PATH = "log.txt";
	public final static int EMPLOYEE = 1;
	public final static int INTERVAL = 2;
	public final static int STATUS = 3;
	public final static int TIME = 4;
	public final static int ERROR = 5;
	public static AtomicInteger ThreadCount = new AtomicInteger(0); 
	public final static String REQUEST_FILE_NAME = "Requests.txt";
	public static Lock outputLock = new ReentrantLock();
}
