package org.zzy.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.zzy.springsecuritydemo.model.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final String ANY_API_ENDPOINT = "/api/**";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers(HttpMethod.GET, ANY_API_ENDPOINT).hasAnyRole(Role.ADMIN.name(), Role.USER.name())
				.antMatchers(HttpMethod.POST, ANY_API_ENDPOINT).hasRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.DELETE, ANY_API_ENDPOINT).hasRole(Role.ADMIN.name())
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
		return http.build();
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				User.builder()
						.username("admin")
						.password(passwordEncoder().encode("admin"))
						.roles(Role.ADMIN.name())
						.build(),
				User.builder()
						.username("user")
						.password(passwordEncoder().encode("user"))
						.roles(Role.USER.name())
						.build()
		);
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
