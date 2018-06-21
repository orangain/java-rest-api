package com.example.resource;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.example.dao.MailDao;
import com.example.dto.ApiError;
import com.example.dto.Mail;
import com.example.dto.variant.MailForCreate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("mails")
public class MailResource extends BaseResource {

	private MailDao openSessionAndGetDao() {
		return new MailDao(this.openSession());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a Mail", tags = { "Mail" })
	@ApiResponse(responseCode = "201", description = "Item successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Mail.class)))
	@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ApiError.class)))
	public Response createMail(@Valid MailForCreate item, @Context UriInfo uriInfo) {
		// Save at first
		try (MailDao dao = this.openSessionAndGetDao()) {
			int numAffected = dao.insertMail(item);
			if (numAffected == 0) {
				throw new InternalServerErrorException("Failed to insert");
			}
			dao.commit();
		}
		int createdItemId = item.getMailId();

		// Send mail
		String smtpHost = System.getenv("SMTP_HOST");
		int smtpPort = Integer.parseInt(System.getenv("SMTP_PORT"));

		Email email = new SimpleEmail();
		email.setHostName(smtpHost);
		email.setSmtpPort(smtpPort);
		try {
			email.setFrom(item.getFrom());
			email.setSubject(item.getSubject());
			email.setMsg(item.getBody());
			email.addTo(item.getTo());
			email.send();

			item.setIsSucceeded(true);
		} catch (EmailException e) {
			item.setIsSucceeded(false);
		}

		// Update result
		try (MailDao dao = this.openSessionAndGetDao()) {
			int numAffected = dao.updateMail(createdItemId, item);
			if (numAffected == 0) {
				throw new InternalServerErrorException("Failed to update");
			}
			dao.commit();
		}

		try (MailDao dao = this.openSessionAndGetDao()) {
			Mail createdItem = dao.getMail(createdItemId);

			// See: https://stackoverflow.com/a/26094619
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			URI location = builder.path(Integer.toString(createdItemId)).build();

			return Response.created(location).entity(createdItem).build();
		}

	}
}
