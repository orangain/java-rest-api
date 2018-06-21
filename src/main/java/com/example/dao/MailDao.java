package com.example.dao;

import org.apache.ibatis.session.SqlSession;

import com.example.dto.Mail;
import com.example.sqlmapper.MailMapper;

public class MailDao extends BaseDao {

	private MailMapper mapper;

	public MailDao(SqlSession session) {
		super(session);
		this.mapper = session.getMapper(MailMapper.class);
	}

	public Mail getMail(Integer mailId) {
		return this.mapper.selectMail(mailId);
	}

	/**
	 * Insert a {@code Mail} and its collections into database.
	 * 
	 * @param item
	 *            a {@code Mail} to insert
	 * @return number of affected rows
	 */
	public int insertMail(Mail item) {
		// Mail
		int numAffected = mapper.insertMail(item);
		if (numAffected == 0) {
			return numAffected; // Failed to insert
		}

		return numAffected;
	}

	/**
	 * Update a {@code Mail} including its collections.
	 * 
	 * @param mailId
	 *            An ID of {@code Mail}.
	 * @param changes
	 *            A {@code MailForUpdate} object to represent changes. Only modified
	 *            properties of the object are changed. When it contains a
	 *            collection, the collection will be updated by DELETE and INSERT
	 *            strategy.
	 * @return number of affected rows
	 */
	public int updateMail(Integer mailId, Mail changes) {
		changes.setMailId(mailId);

		int numAffected = 0;
		// Mail
		numAffected = mapper.updateMail(changes);
		if (numAffected == 0) {
			return numAffected; // Failed to insert
		}

		return numAffected;
	}
}
