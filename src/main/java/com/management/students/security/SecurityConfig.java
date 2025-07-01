package com.management.students.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.management.students.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthFilter;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors().and().csrf().disable().authorizeHttpRequests(auth->auth
				
		.requestMatchers("/auth/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**").permitAll()
		.requestMatchers(HttpMethod.GET,"/students/me").hasRole("STUDENT")
		.requestMatchers(HttpMethod.GET,"/students/**").hasAnyRole("STAFF","ADMIN")
		.requestMatchers(HttpMethod.GET,"/students/{id}/courses").hasRole("STUDENT")
		.requestMatchers(HttpMethod.PATCH,"/students/{id}").hasAnyRole("STUDENT","ADMIN")
		.requestMatchers(HttpMethod.PATCH,"/students/restore/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.POST,"/students/bulk").hasRole("ADMIN")
		.requestMatchers(HttpMethod.POST,"/students").hasRole("STUDENT")
		.requestMatchers(HttpMethod.POST,"/students/{id}/courses").hasAnyRole("STUDENT","ADMIN")
		.requestMatchers(HttpMethod.DELETE,"/students/**").hasAnyRole("ADMIN","STUDENT")
		.requestMatchers("/staffs/**").hasAnyRole("STAFF","ADMIN")
		.requestMatchers(HttpMethod.GET,"/staffs/{id}").hasAnyRole("STAFF","ADMIN","STUDENT")
		.requestMatchers("/roles/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.POST,"/exam-results/**").hasRole("STAFF")
		.requestMatchers(HttpMethod.GET,"/exam-results").hasRole("STAFF")
		.requestMatchers(HttpMethod.GET,"/exam-results/student/{studentId}").hasRole("STUDENT")
		.requestMatchers(HttpMethod.GET,"/exam-results/exam/{examId}").hasAnyRole("STAFF","STUDENT")
		.requestMatchers(HttpMethod.DELETE,"/exam-results/**").hasRole("STAFF")
		.anyRequest().authenticated())
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
		.build();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
			}
		};
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		// TODO Auto-generated method stub
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
