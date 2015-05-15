package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public final class ReadIntervalRequest extends ReadRequest {
	public long low;
	public long up;
	
	public ReadIntervalRequest(long low, long up){
		super();
		this.low = low;
		this.up = up;
	}
	
	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.INTERVAL;
	}
}
