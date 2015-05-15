package edu.thu.ss.logserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import edu.thu.ss.logserver.Global.Type;
import edu.thu.ss.logserver.request.*;

public class LogClient implements Runnable {

	private BlockingQueue<Request> queue;
	
	public LogClient(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		File RequestsFile = new File(Global.REQUEST_FILE_NAME);
		try{
			FileReader fr = new FileReader(RequestsFile);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null){
				System.out.println(line);
				String[] requestSplit = null;
				requestSplit = line.split(" ");
				if (requestSplit[0].equals("write")){
					if (requestSplit.length != 5){
						System.out.println("error");
						line = br.readLine();
						continue;
					}
					long TimeStamp;
					try{
						TimeStamp = Long.valueOf(requestSplit[1]).longValue();
					}catch (Exception e){
						System.out.println("error");
						line = br.readLine();
						continue;
					}
					Request request;
					Type type = null;
					if (requestSplit[4].equals("enter"))
						type = Type.Enter;
					else if (requestSplit[4].equals("leave"))
						type = Type.Leave;
					if (type == null){
						System.out.println("error");
						line = br.readLine();
						continue;
					}	
					request = new WriteRequest(TimeStamp, requestSplit[2], requestSplit[3], type);
					queue.add(request);
				}else if (requestSplit[0].equals("read")){
					if (requestSplit.length == 1){
						System.out.println("error");
						line = br.readLine();
						continue;
					}
					Request request;
					if (requestSplit[1].equals("employee")){
						if (requestSplit.length != 3){
							System.out.println("error");
							line = br.readLine();
							continue;
						}
						String EmployeeName = requestSplit[2];
						request = new ReadEmployeeRequest(EmployeeName);
						queue.add(request);
					}else if (requestSplit[1].equals("interval")){
						if (requestSplit.length != 4){
							System.out.println("error");
							line = br.readLine();
							continue;
						}
						long low, up;
						try{
							low = Long.valueOf(requestSplit[2]).longValue();
							up = Long.valueOf(requestSplit[3]).longValue();
						}catch (Exception e){
							System.out.println("error");
							line = br.readLine();
							continue;
						}
						request = new ReadIntervalRequest(low, up);
						queue.add(request);
					}else if (requestSplit[1].equals("status")){
						if (requestSplit.length != 2){
							System.out.println("error");
							line = br.readLine();
							continue;
						}
						request = new ReadStatusRequest();
						queue.add(request);
					}else if (requestSplit[1].equals("time")){
						if (requestSplit.length != 4){
							System.out.println("error");
							line = br.readLine();
							continue;
						}
						String EmployeeName = requestSplit[2];
						String RoomId = requestSplit[3];
						request = new ReadTimeRequest(EmployeeName, RoomId);
						queue.add(request);
					}else{
						System.out.println("error");
						line = br.readLine();
						continue;
					}
				}else{
					System.out.println("error");
				}
				line = br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
