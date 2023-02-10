package com.eduservice.demo.authentication;

import static com.eduservice.demo.model.Utente.ADMIN_ROLE;


import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired DataSource datasource;
	

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // authorization paragraph: qui definiamo chi può accedere a cosa
		.antMatchers(HttpMethod.GET, "/", "/login", "/register", "/css/**", "/images/**").permitAll() //chiunque (autenticato o no) può accedere alle pagine  login, register, ai css e alle immagini
		.antMatchers(HttpMethod.POST, "/login", "/register").permitAll() // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
		.antMatchers(HttpMethod.GET, "/**/add/**","/**/edit/**","/**/delete/**").hasAnyAuthority(ADMIN_ROLE) // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
		.antMatchers(HttpMethod.GET, "/**/add/**","/**/edit/**","/**/delete/**").hasAnyAuthority(ADMIN_ROLE)
		.anyRequest().authenticated() // tutti gli utenti autenticati possono accere alle pagine rimanenti
		// login paragraph: qui definiamo come è gestita l'autenticazione
		.and().formLogin() // usiamo il protocollo formlogin 
		.loginPage("/login") // la pagina di login si trova a /login
		.defaultSuccessUrl("/default") // se il login ha successo, si viene rediretti al path /default
		// logout paragraph: qui definiamo il logout
		.and()
		.logout()
		.logoutUrl("/logout") // il logout è attivato con una richiesta GET a "/logout"
		.logoutSuccessUrl("/") // in caso di successo, si viene reindirizzati alla /index page
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.clearAuthentication(true).permitAll()
		.and().cors().and().csrf().disable();
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		//per accedere alle credenziali salvate
		.dataSource(this.datasource)
		//recuperiamo username e ruolo
		.authoritiesByUsernameQuery("SELECT username, role FROM utente WHERE username=?::integer")
		//recuperiamo username, password e un flag
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM utente WHERE username=?::integer");
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}