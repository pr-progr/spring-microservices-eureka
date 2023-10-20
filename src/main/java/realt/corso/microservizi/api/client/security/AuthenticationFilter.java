package realt.corso.microservizi.api.client.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import realt.corso.microservizi.api.client.model.LoginUserModel;
import realt.corso.microservizi.api.client.service.UserService;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;
	private Environment environment;

	public AuthenticationFilter(UserService userService, Environment environment,
			AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.userService = userService;
		this.environment = environment;
	}
	
	public AuthenticationFilter(
			AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginUserModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginUserModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEemail(), creds.getPasswoord(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {


	}
}
