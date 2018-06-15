package com.example.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.example.dto.ApiError;

public class ClientErrorMapper implements ExceptionMapper<ClientErrorException> {

	@Override
	public Response toResponse(ClientErrorException exception) {
		if (exception.getResponse().getStatus() != 404) {
			exception.printStackTrace();
		}

		ApiError error = new ApiError(exception.getMessage());
		return Response.status(exception.getResponse().getStatusInfo()).entity(error).build();
	}

}
