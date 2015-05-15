package edu.thu.ss.logserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Global {
	static String LogFileName;
	
	public static String LOG_FILE_PATH;
	public final static int EMPLOYEE = 1;
	public final static int INTERVAL = 2;
	public final static int STATUS = 3;
	public final static int TIME = 4;
	public static AtomicInteger ThreadCount = new AtomicInteger(0); 
	public final static String REQUEST_FILE_NAME = "Requests.txt";
	public static Lock outputLock = new ReentrantLock();
}
