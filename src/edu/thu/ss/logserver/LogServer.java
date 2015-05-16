package edu.thu.ss.logserver;

import java.util.concurrent.BlockingQueue;

import edu.thu.ss.logserver.processor.ReadEmployeeProcessor;
import edu.thu.ss.logserver.processor.ReadIntervalProcessor;
import edu.thu.ss.logserver.processor.ReadProcessor;
import edu.thu.ss.logserver.processor.ReadStatusProcessor;
import edu.thu.ss.logserver.processor.ReadTimeProcessor;
import edu.thu.ss.logserver.processor.WriteProcessor;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.util.ResponseUtil;

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
				if (request.isRead()){//readRequest
					Global.ThreadCount.incrementAndGet();
					switch (request.readType()){
					case Global.ERROR:
						Global.ThreadCount.decrementAndGet();
						Global.response(request.id, "error");
						break;
					case Global.EMPLOYEE:
						new Thread(new ReadEmployeeProcessor(request)).start();
						break;
					case Global.INTERVAL:
						new Thread(new ReadIntervalProcessor(request)).start();
						break;
					case Global.STATUS:
						new Thread(new ReadStatusProcessor(request)).start();
						break;
					case Global.TIME:
						new Thread(new ReadTimeProcessor(request)).start();
						break;
					}
				}else{ //writeRequest
					while(Global.ThreadCount.get() != 0){
						Thread.sleep(50);
					}
					//Global.ThreadCount.incrementAndGet();
					new WriteProcessor(request);
					
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
