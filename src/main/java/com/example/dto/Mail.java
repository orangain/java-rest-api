package com.example.dto;

import java.util.List;

public class Mail {
	private Integer mailId;
	private String from;
	private String to;
	private String cc;
	private String subject;
	private String body;
	private Boolean isSucceeded;
	private List<AttachmentInMail> attachments;

	public Integer getMailId() {
		return mailId;
	}

	public void setMailId(Integer mailId) {
		this.mailId = mailId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getIsSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public List<AttachmentInMail> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentInMail> attachments) {
		this.attachments = attachments;
	}

	public static class AttachmentInMail {
		private Integer mailId;
		private Integer index;
		private String filename;
		private Long size;
		private byte[] content;

		public Integer getMailId() {
			return mailId;
		}

		public void setMailId(Integer mailId) {
			this.mailId = mailId;
		}

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public Long getSize() {
			return size;
		}

		public void setSize(Long size) {
			this.size = size;
		}

		public byte[] getContent() {
			return content;
		}

		public void setContent(byte[] content) {
			this.content = content;
		}
	}
}
