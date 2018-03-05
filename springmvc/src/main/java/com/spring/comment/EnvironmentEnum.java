package com.spring.comment;

public enum EnvironmentEnum {

	HOME("home", "家"),COMPANY("company", "公司");

	public String name;
	public String description;

	private EnvironmentEnum(String name, String description) {
		this.description = description;
		this.name = name;
	}
}
