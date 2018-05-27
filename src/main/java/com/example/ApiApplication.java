package com.example;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.glassfish.jersey.server.ResourceConfig;

public class ApiApplication extends ResourceConfig {
	SqlSessionFactory sqlSessionFactory;

	public ApiApplication() throws IOException {
		packages("com.example");

		try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
			this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		}
	}

	SqlSession openSession() {
		return this.sqlSessionFactory.openSession();
	}
}
