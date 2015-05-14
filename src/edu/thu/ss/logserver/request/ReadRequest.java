package edu.thu.ss.logserver.request;

public abstract class ReadRequest extends Request {

	@Override
	public boolean isRead() {
		return true;
	}

	@Override
	public boolean isWrite() {
		return false;
	}
}
