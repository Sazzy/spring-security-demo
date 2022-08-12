package org.zzy.springsecuritydemo.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zzy.springsecuritydemo.model.Status;
import org.zzy.springsecuritydemo.model.User;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Data
public class SecurityUser implements UserDetails {
	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;
	private final boolean isActive;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonExpired();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isAccountNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return isAccountNonExpired();
	}

	public static UserDetails from(User user) {
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				user.getStatus() == Status.ACTIVE,
				user.getStatus() == Status.ACTIVE,
				user.getStatus() == Status.ACTIVE,
				user.getStatus() == Status.ACTIVE,
				user.getRole().getAuthorities()
		);
	}
}
