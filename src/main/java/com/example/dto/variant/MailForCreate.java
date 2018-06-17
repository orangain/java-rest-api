package com.example.dto.variant;

import javax.validation.constraints.NotNull;

import com.example.dto.Mail;

public class MailForCreate extends Mail {

	@NotNull
	@Override
	public String getFrom() {
		return super.getFrom();
	}

	@NotNull
	@Override
	public String getTo() {
		return super.getTo();
	}

	@NotNull
	@Override
	public String getSubject() {
		return super.getSubject();
	}

	@NotNull
	@Override
	public String getBody() {
		return super.getBody();
	}

}
