package com.example;

import java.io.IOException;

public class ApiTestApplication extends ApiApplication {

	public ApiTestApplication() throws IOException {
		super();
		register(StackTraceExceptionMapper.class);
	}

}
