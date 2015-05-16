package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public final class ErrorRequest extends ReadRequest {
	
	public ErrorRequest(){
		super();
	}
	
	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.ERROR;
	}
}
