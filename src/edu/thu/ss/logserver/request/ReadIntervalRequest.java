package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public final class ReadIntervalRequest extends ReadRequest {
	public long low;
	public long up;
	
	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.INTERVAL;
	}
}
