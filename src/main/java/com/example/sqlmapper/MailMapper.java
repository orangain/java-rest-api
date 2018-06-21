package com.example.sqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.dto.Mail;
import com.example.dto.Mail.AttachmentInMail;

/**
 * MyBatis mapper interface for Mail.
 *
 */
public interface MailMapper {

	Mail selectMail(@Param("mailId") Integer mailId);

	/**
	 * Insert a {@code Mail} into database.
	 * 
	 * @param item
	 *            a {@code Mail} object to insert
	 * @return number of affected rows
	 */
	int insertMail(Mail item);

	/**
	 * Update a {@code Mail} in database.
	 * 
	 * @param changes
	 *            A {@code Mail} object to represent changes. Only modified
	 *            properties of the object are changed.
	 * @return number of affected rows
	 */
	int updateMail(Mail changes);

	int insertAttachments(List<AttachmentInMail> attachments);

	AttachmentInMail selectAttachment(@Param("mailId") Integer mailId, @Param("index") Integer index);
}
