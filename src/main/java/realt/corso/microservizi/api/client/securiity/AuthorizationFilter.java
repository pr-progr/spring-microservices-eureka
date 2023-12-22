package realt.corso.microservizi.api.client.securiity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Header;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	Environment environment;

	public AuthorizationFilter(AuthenticationManager authenticationManager,Environment environment) {
		super(authenticationManager);
		this.environment=environment;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

	String header = request.getHeader(environment.getProperty("header.name"));
	if(header==null) return;
	
	boolean isPrefixBearer = header.startsWith(environment.getProperty("header.prefix"));
	
	if(!isPrefixBearer) return;
	
	UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	chain.doFilter(request, response);
	
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String header) {
		String token = header.replace(environment.getProperty("header.prefix"),"");
		
		String secretToken = environment.getProperty("secret.token");
		byte[] secretTokenByte = Base64.getEncoder().encode(secretToken.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretTokenByte, SIG.HS512.toString());
		
	 JwtParser jwtParser = Jwts.parser()
		.verifyWith(secretKey)
		.build();
		
	 Jwt<?,?> jwt =  jwtParser.parse(token);
	 Claims objClaims = (Claims) jwt.getPayload();
	 String idUser = objClaims.getSubject();
	 
	 return new UsernamePasswordAuthenticationToken(idUser, null, new ArrayList<>());
	}

}
