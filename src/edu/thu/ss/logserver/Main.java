package edu.thu.ss.logserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.thu.ss.logserver.request.Request;

public class Main {

	public static void main(String[] args) {
		BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
		Thread client = new Thread(new LogClient(queue));
		client.start();

		LogServer server = new LogServer(queue);
		server.start();
	}

}
