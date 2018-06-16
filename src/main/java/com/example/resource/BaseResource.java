package com.example.resource;

import javax.inject.Inject;
import javax.ws.rs.core.Application;

import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.client.RestHighLevelClient;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;

public class BaseResource {
	@Inject
	Application application;

	protected ApiApplication getApplication() {
		return (ApiApplication) ((ResourceConfig) this.application).getApplication();
	}

	protected SqlSession openSession() {
		return this.getApplication().openSession();
	}

	public RestHighLevelClient getEsClient() {
		return this.getApplication().getEsClient();
	}
}
