package org.zzy.springsecuritydemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
	DEVELOPERS_READ("developers:read"),
	DEVELOPERS_WRITE("developers:write");

	private final String name;
}
