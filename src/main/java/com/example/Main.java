package com.example;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import com.example.entity.Customer;

public class Main {
	public static void main(String[] args) throws Exception {
		try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
			try (SqlSession session = factory.openSession()) {
				List<Customer> result = session.selectList("com.example.customerSelect");

				result.forEach(customer -> {
					System.out.println(customer);
				});
			}
		}

		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		server.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "com.example");

		server.start();
		server.join();
	}
}
