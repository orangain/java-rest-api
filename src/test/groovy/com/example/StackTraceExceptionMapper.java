package com.example;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * ExceptionMapper to print stack trace of exception in server side. This makes
 * it easy to debug failure in JerseyTest.
 *
 */
@Provider
public class StackTraceExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		exception.printStackTrace();
		return Response.serverError().entity("Server error").build();
	}

}
