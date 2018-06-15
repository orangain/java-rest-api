package com.example.exception;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.dto.ApiError;

@Provider
public class ServerErrorMapper implements ExceptionMapper<ServerErrorException> {

	@Override
	public Response toResponse(ServerErrorException exception) {
		ApiError error = new ApiError(exception.getMessage());
		return Response.status(exception.getResponse().getStatusInfo()).entity(error).build();
	}

}
