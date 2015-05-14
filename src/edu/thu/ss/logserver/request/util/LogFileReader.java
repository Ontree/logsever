package edu.thu.ss.logserver.request.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class LogFileReader extends BufferedReader {

	public LogFileReader(File file) throws IOException {
		super(new FileReader(file));
	}

	public void startRead() {

	}

	public void endRead() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
