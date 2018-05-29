package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;

import groovy.sql.Sql;

public class ApiTestBase extends JerseyTest {

	@Override
	protected Application configure() {
		try {
			return new ApiApplication();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Sql sqlInstance;

	public static void resetDB() {
		runSql("/sql/sakila-schema.sql");
		runSql("/sql/sakila-data.sql");
	}

	public static void runSql(String resourceName) {
		try {
			final Sql sql = ApiTestBase.getSqlInstance();
			// See: https://stackoverflow.com/a/18897411
			try (Scanner scanner = new Scanner(ApiTestBase.class.getResourceAsStream(resourceName), "UTF-8")) {
				final String sqlText = scanner.useDelimiter("\\A").next();
				sql.execute(sqlText);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static Sql getSqlInstance() throws SQLException {
		if (sqlInstance == null) {
			sqlInstance = newSqlInstance();
		}
		return sqlInstance;
	}

	private static Sql newSqlInstance() throws SQLException {
		String url = System.getenv("DATABASE_URL");
		Properties properties = new Properties();
		properties.setProperty("allowMultiQueries", "true");

		return Sql.newInstance(url, properties);
	}
}