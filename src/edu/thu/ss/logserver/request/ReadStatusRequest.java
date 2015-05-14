package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public class ReadStatusRequest extends ReadRequest{

	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.STATUS;
	}

}
