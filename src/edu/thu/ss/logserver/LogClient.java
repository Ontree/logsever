package edu.thu.ss.logserver;

import java.util.concurrent.BlockingQueue;

import edu.thu.ss.logserver.request.Request;

public class LogClient implements Runnable {

	private BlockingQueue<Request> queue;
	
	public LogClient(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		//send request here
		//queue.add(...)
		
	}

}
