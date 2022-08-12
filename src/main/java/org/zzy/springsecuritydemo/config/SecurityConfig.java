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
import org.zzy.springsecuritydemo.model.Permission;
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
				.antMatchers(HttpMethod.GET, ANY_API_ENDPOINT).hasAuthority(Permission.DEVELOPERS_READ.getName())
				.antMatchers(HttpMethod.POST, ANY_API_ENDPOINT).hasAuthority(Permission.DEVELOPERS_WRITE.getName())
				.antMatchers(HttpMethod.DELETE, ANY_API_ENDPOINT).hasAuthority(Permission.DEVELOPERS_WRITE.getName())
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
						.authorities(Role.ADMIN.getAuthorities())
						.build(),
				User.builder()
						.username("user")
						.password(passwordEncoder().encode("user"))
						.authorities(Role.USER.getAuthorities())
						.build()
		);
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
