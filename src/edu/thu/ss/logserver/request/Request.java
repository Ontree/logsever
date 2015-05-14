package edu.thu.ss.logserver.request;

public abstract class Request {
	private static volatile int nextId = 0;

	public final int id = (++nextId);

	public abstract boolean isRead();

	public abstract boolean isWrite();
}
