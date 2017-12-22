package com.hyperats.cache.test;

public class MethodCallBean {
	public static final String DEFAULT_VALUE = "default value";
	public static final String CHANGED_VALUE = "changed value";

	private String value;
	
	public void setValue(String v) {
		value = v;
	}
	
	public String getValue() {
		return value;
	}
}
