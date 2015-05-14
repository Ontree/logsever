package edu.thu.ss.logserver.processor;


public class LogItem {
	public enum Type {
		Enter, Leave;
	}
	
	public long timestmp;
	public String name;
	public String roomId;
	public Type type;
}
