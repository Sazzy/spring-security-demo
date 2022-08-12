package org.zzy.springsecuritydemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
	USER(Set.of(Permission.DEVELOPERS_READ)),
	ADMIN(Set.of(Permission.DEVELOPERS_WRITE, Permission.DEVELOPERS_READ));

	private final Set<Permission> permissions;

	public Set<SimpleGrantedAuthority> getAuthorities() {
		return getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getName()))
				.collect(Collectors.toSet());
	}
}
