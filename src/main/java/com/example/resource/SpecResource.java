package com.example.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import io.swagger.v3.oas.annotations.Hidden;

@Path("/spec")
public class SpecResource {

	@GET
	@Produces(MediaType.WILDCARD)
	@Hidden
	public InputStream getSwaggerUI(@Context UriInfo uriInfo) {
		if (!uriInfo.getPath().endsWith("/")) {
			URI uri = uriInfo.getAbsolutePathBuilder().path("/").build();
			throw new RedirectionException(Status.SEE_OTHER, uri);
		}

		URI specJsonUri = uriInfo.getBaseUriBuilder().path("/openapi.json").build();

		InputStream inputStream = this.getFile("index.html");
		// See: https://stackoverflow.com/a/18897411
		try (Scanner scanner = new Scanner(inputStream)) {
			String html = scanner.useDelimiter("\\A").next();
			html = html.replace("http://petstore.swagger.io/v2/swagger.json", specJsonUri.toString());
			return new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
		}
	}

	@GET
	@Path("/{path: .+}")
	@Produces(MediaType.WILDCARD)
	@Hidden
	public InputStream getFile(@PathParam("path") String path) {
		final String prefix = "/swagger-ui-dist/";
		URI baseUri;
		try {
			baseUri = new URI("http://example.com" + prefix); // host name is ignored
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		final URI fileUri = baseUri.resolve(path).normalize();
		final String resourcePath = fileUri.getPath();
		if (!resourcePath.startsWith(prefix)) {
			throw new NotFoundException();
		}

		final InputStream stream = this.getClass().getResourceAsStream(resourcePath);
		if (stream == null) {
			throw new NotFoundException();
		}
		return stream;
	}
}
