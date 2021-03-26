
package com.app.gamaacademy.cabrasdoagrest.bankline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.gamaacademy.cabrasdoagrest.bankline.security.jwt.JWTAuthorizationFilter;

@Configuration

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] SWAGGER_WHITELIST = { "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**" };

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				// .authorizeRequests().antMatchers("**").permitAll();
				// JWT
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				// Não cobra autentitação das páginas do swagger
				.antMatchers(SWAGGER_WHITELIST).permitAll().antMatchers("/h2-console/**").permitAll()
				.antMatchers("/login").permitAll()
				// .antMatchers(HttpMethod.POST).hasRole("ADMIN")
				// Cobra Autenticação das outras rotas
				.anyRequest().authenticated()
				// .and().httpBasic()
				// Autenticação stateless
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
