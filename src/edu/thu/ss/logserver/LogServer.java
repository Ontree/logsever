package edu.thu.ss.logserver;

import java.util.concurrent.BlockingQueue;

import edu.thu.ss.logserver.request.Request;

public class LogServer {
	private BlockingQueue<Request> queue;

	public LogServer(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	public void start() {
		while (true) {
			try {
				Request request = queue.take();
				//process request
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
