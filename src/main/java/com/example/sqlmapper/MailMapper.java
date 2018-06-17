package com.example.sqlmapper;

import com.example.dto.Mail;

/**
 * MyBatis mapper interface for Mail.
 *
 */
public interface MailMapper {

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
}
