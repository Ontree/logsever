package edu.thu.ss.logserver.request;

import edu.thu.ss.logserver.Global;

public final class ReadEmployeeRequest extends ReadRequest {
	public String name;

	public ReadEmployeeRequest(String name){
		super();
		this.name = name;
	}
	
	@Override
	public int readType() {
		// TODO Auto-generated method stub
		return Global.EMPLOYEE;
	}
}
