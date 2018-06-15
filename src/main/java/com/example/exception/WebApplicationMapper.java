package com.example.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.dto.ApiError;

@Provider
public class WebApplicationMapper implements ExceptionMapper<WebApplicationException> {

	@Override
	public Response toResponse(WebApplicationException exception) {
		if (exception.getResponse().getStatus() < 400) {
			return exception.getResponse(); // Ignore redirects
		}

		if (exception.getResponse().getStatus() != 404) {
			exception.printStackTrace();
		}

		ApiError error = new ApiError(exception.getMessage());
		return Response.status(exception.getResponse().getStatusInfo()).entity(error).build();
	}

}
