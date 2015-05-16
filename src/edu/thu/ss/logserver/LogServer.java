package edu.thu.ss.logserver;

import java.util.concurrent.BlockingQueue;

import edu.thu.ss.logserver.processor.ReadEmployeeProcessor;
import edu.thu.ss.logserver.processor.ReadIntervalProcessor;
import edu.thu.ss.logserver.processor.ReadLogProcessor;
import edu.thu.ss.logserver.processor.ReadProcessor;
import edu.thu.ss.logserver.processor.ReadStatusProcessor;
import edu.thu.ss.logserver.processor.ReadTimeProcessor;
import edu.thu.ss.logserver.processor.WriteProcessor;
import edu.thu.ss.logserver.request.ReadEmployeeRequest;
import edu.thu.ss.logserver.request.ReadIntervalRequest;
import edu.thu.ss.logserver.request.ReadStatusRequest;
import edu.thu.ss.logserver.request.ReadTimeRequest;
import edu.thu.ss.logserver.request.Request;
import edu.thu.ss.logserver.request.WriteRequest;
import edu.thu.ss.logserver.request.util.ResponseUtil;

public class LogServer {
	private BlockingQueue<Request> queue;

	public LogServer(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	public void startReadRequest(Request request){
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
	}
	public boolean conflict(Request request, Request request2){
		WriteRequest request1 = (WriteRequest)request;
		switch (request2.readType()){
			case Global.ERROR:
				return false;
			case Global.EMPLOYEE:
				if (((ReadEmployeeRequest)request2).name.equals(request1.name)
						&& request1.type.equals(Global.Type.valueOf("Enter"))){
					return true;
				}else{
					return false;
				}
			case Global.INTERVAL:
				long low = ((ReadIntervalRequest)request2).low;
				long up = ((ReadIntervalRequest)request2).up;
				if (request1.timestmp < low || request1.timestmp > up){
					return false;
				}else{
					return true;
				}
			case Global.STATUS:
				return true;
			case Global.TIME:
				String name = ((ReadTimeRequest)request2).name;
				String roomId = ((ReadTimeRequest)request2).roomId;
				if (request1.type.equals(Global.Type.valueOf("Leave"))
						&& name.equals(request1.name) 
						&& roomId.equals(request1.roomId)){
					return true;
				}else{
					return false;
				}
			default:
				return false;
		}
	}
	public void start() {
		Request request;
		Request requestBlock = null;
		while (true) {
			try {
				if (requestBlock != null){
					request = requestBlock;
					requestBlock = null;
				}else{
					request = queue.take();
				}
				
				//process request
				if (request.isRead()){//readRequest
					startReadRequest(request);
				}else{ //writeRequest
					
					Global.ThreadCount.incrementAndGet();
					new Thread(new ReadLogProcessor(request)).start();

					while(true){
						if (queue.isEmpty()){
							break;
						}
						Request request2 = queue.take();
						if (request2.isRead()){//readRequest
							if (!conflict(request,request2)){
								startReadRequest(request2);
							}else{
								requestBlock = request2;
								break;
							}
						}else{
							requestBlock = request2;
							break;
						}	
					}
					
					
					while(Global.ThreadCount.get() != 0){
						Thread.sleep(50);
					}
					Global.Block.incrementAndGet();
					new Thread(new WriteProcessor(request)).start();
					while(Global.Block.get()!=0){
						Thread.sleep(50);
					}
					
					/*
					while(Global.ThreadCount.get() != 0){
						Thread.sleep(50);
					}
					new WriteProcessor(request);
					*/
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
