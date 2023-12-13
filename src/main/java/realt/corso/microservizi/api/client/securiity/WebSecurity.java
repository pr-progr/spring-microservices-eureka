package realt.corso.microservizi.api.client.securiity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import realt.corso.microservizi.api.client.service.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurity {
	
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment env;
	


	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,Environment env) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.env=env;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
    	// Create AuthenticationFilter
    	AuthenticationFilter authenticationFilter = 
    			new AuthenticationFilter(authenticationManager, userService, env);
    	authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(
				req -> req
					.requestMatchers(new AntPathRequestMatcher("/users/**", HttpMethod.POST.toString())).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/users/status/check", HttpMethod.GET.toString())).permitAll()
					)
			.addFilter(authenticationFilter)
			.authenticationManager(authenticationManager)
			.sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
		
		return http.build();
		
	}
}
