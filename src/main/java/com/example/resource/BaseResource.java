package com.example.resource;

import javax.inject.Inject;
import javax.ws.rs.core.Application;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;

public class BaseResource {
	@Inject
	Application application;

	protected SqlSession openSession() {
		ApiApplication application = (ApiApplication) ((ResourceConfig) this.application).getApplication();
		return application.openSession();
	}

}
