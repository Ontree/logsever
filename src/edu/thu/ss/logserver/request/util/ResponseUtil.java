package edu.thu.ss.logserver.request.util;

public class ResponseUtil {

	/**
	 * must be called for response, may subject to change
	 * @param requestId
	 * @param response
	 */
	public static void response(int requestId, String response) {
		System.out.println("Response for:" + requestId);
		System.out.println(response);
	}
}
