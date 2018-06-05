package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.glassfish.jersey.server.ResourceConfig;

public class ApiApplication extends ResourceConfig {
	SqlSessionFactory sqlSessionFactory;

	public ApiApplication() throws IOException {
		packages("com.example");
		packages("io.swagger.v3.jaxrs2.integration.resources"); // OpenAPI spec

		Properties properties = new Properties();
		if (System.getenv("JDBC_DRIVER") != null) {
			properties.setProperty("JDBC_DRIVER", System.getenv("JDBC_DRIVER"));
		}
		if (System.getenv("JDBC_URL") != null) {
			properties.setProperty("JDBC_URL", System.getenv("JDBC_URL"));
		}

		try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
			this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(in, properties);
		}
	}

	public SqlSession openSession() {
		return this.sqlSessionFactory.openSession();
	}
}
