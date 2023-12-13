package realt.corso.microservizi.api.client.securiity;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import realt.corso.microservizi.api.client.dto.UserDto;
import realt.corso.microservizi.api.client.model.LoginUserModel;
import realt.corso.microservizi.api.client.service.UserService;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserService userService;
	private Environment env;

	public AuthenticationFilter(AuthenticationManager authenticationManager,UserService userService, Environment env) {
		super(authenticationManager);
		this.userService = userService;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			LoginUserModel loginUserModel = new ObjectMapper().readValue(request.getInputStream(), LoginUserModel.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							loginUserModel.getEmail(), 
							loginUserModel.getPassword(),
							new ArrayList<>()));
		
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String email = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDto=userService.getUserDetailsByUsername(email);
	
		String tokenSecret = env.getProperty("secret.token");
		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, Keys.hmacShaKeyFor(secretKeyBytes).getAlgorithm());
		
		
		Instant now = Instant.now();
		String token = Jwts.builder()
			.subject(userDto.getUserId())
			.expiration(Date.from(now.plusMillis(3600000)))
			.issuedAt(Date.from(now))
			.signWith(secretKey,Jwts.SIG.HS512)
			.compact();
			
			
			response.addHeader("token", token);
			response.addHeader("idUser", userDto.getUserId());
	}
}
