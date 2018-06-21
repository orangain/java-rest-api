package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.LogManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.test.JerseyTest;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.sql.Sql;

public class ApiTestBase extends JerseyTest {

	@Override
	protected Application configure() {
		// Configure logging not to print INFO logs.
		// Note: setting via `systemProperty 'java.util.logging.config.file',
		// 'logging.properties'` in build.gradle does not work.
		// See:
		// https://discuss.gradle.org/t/unable-to-configure-java-util-logging-with-gradle-test-task/2034
		try {
			LogManager.getLogManager().readConfiguration(ApiTestBase.class.getResourceAsStream("/logging.properties"));
		} catch (SecurityException | IOException e) {
			throw new RuntimeException(e);
		}

		try {
			return new ApiTestApplication();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void configureClient(final ClientConfig config) {
		// Allow changing HTTP method to use PATCH.
		config.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}

	private static Sql sqlInstance;

	public static void resetDB() {
		runSql("/sql/sakila-schema.sql");
		runSql("/sql/sakila-data.sql");
		runSql("/sql/custom.sql");
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

	protected JsonSlurper jsonSlurper = new JsonSlurper();

	protected Object parseJsonResponse(Response response) {
		String jsonText = response.readEntity(String.class);
		return this.jsonSlurper.parseText(jsonText);
	}

	protected Entity<String> buildJsonEntity(Object object) {
		return Entity.json(JsonOutput.toJson(object));
	}
}
