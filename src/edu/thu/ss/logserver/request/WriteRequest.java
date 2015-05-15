package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global.Type;

public final class WriteRequest extends Request {

	public long timestmp;
	public String name;
	public String roomId;
	public Type type;

	public WriteRequest(long timestmp, String name, String roomId, Type type) {
		super();
		this.timestmp = timestmp;
		this.name = name;
		this.roomId = roomId;
		this.type = type;
	}

	@Override
	public boolean isRead() {
		return false;
	}

	@Override
	public boolean isWrite() {
		return true;
	}

	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return 0;
	}
}
