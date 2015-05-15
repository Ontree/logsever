package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public final class ReadTimeRequest extends ReadRequest{
	public String name;
	public String roomId;
	
	public ReadTimeRequest(String name, String roomId){
		super();
		this.name = name;
		this.roomId = roomId;
	}
	
	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.TIME;
	}
}
