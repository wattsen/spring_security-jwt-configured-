package io.linear.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtAuthEntryPoint jwtAuthEntryPoint;


	public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
	}


	// Configuring Chain of Filters.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
		http
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/api/v1/auth/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.httpBasic();
		http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthFilter jwtAuthFilter() {
		return new JwtAuthFilter();
	}
}
