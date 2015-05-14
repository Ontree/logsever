package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public final class ReadTimeRequest extends ReadRequest{
	public String name;
	public String roomId;
	
	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.TIME;
	}
}
