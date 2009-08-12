package com.kousenit.dao;

import java.util.HashMap;
import java.util.Map;

import com.google.apphosting.api.ApiProxy;

class TestEnvironment implements ApiProxy.Environment {
	public String getAppId() {
		return "Unit Tests";
	}

	public String getVersionId() {
		return "1.0";
	}

	public String getRequestNamespace() {
		return "";
	}

	public String getAuthDomain() {
		return "gmail.com";
	}

	public boolean isLoggedIn() {
		return false;
	}

	public String getEmail() {
		return "";
	}

	public boolean isAdmin() {
		return false;
	}
	
	public Map<String, Object> getAttributes() {
		return new HashMap<String, Object>();
	}
}
