package com.example.dao;

import java.io.Closeable;

import org.apache.ibatis.session.SqlSession;

public class BaseDao implements Closeable {

	private SqlSession session;

	public BaseDao(SqlSession session) {
		this.session = session;
	}

	public SqlSession getSession() {
		return this.session;
	}

	@Override
	public void close() {
		this.session.close();
	}

	public void commit() {
		this.session.commit();
	}
}
