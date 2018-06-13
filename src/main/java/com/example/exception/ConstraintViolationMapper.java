package com.example.exception;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.dto.ApiError;
import com.example.dto.ApiErrorDetail;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		ApiError error = new ApiError("Validation Error");
		error.setDetails(exception.getConstraintViolations().stream().map(v -> {
			List<String> path = StreamSupport.stream(v.getPropertyPath().spliterator(), false)
					.filter(n -> !n.getKind().equals(ElementKind.METHOD) && !n.getKind().equals(ElementKind.PARAMETER))
					.map(n -> n.getName()).collect(Collectors.toList());
			return new ApiErrorDetail(v.getMessage(), path);
		}).collect(Collectors.toList()));
		return Response.status(Status.BAD_REQUEST).entity(error).build();
	}

}
