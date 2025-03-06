package com.ffozdemir.librarymanagement.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType {

	ADMIN("Admin"), STAFF("Staff"), MEMBER("Member");

	RoleType(String name) {
		this.name = name;
	}

	public final String name;
}
