package realt.corso.microservizi.api;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	
	@Autowired
	Environment env;
	
	
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}


	public static class Config {
		
	}

	@Override
	public GatewayFilter apply(Config config) {
		
		
			return (exchange,chain)->{
			org.springframework.http.server.reactive.ServerHttpRequest request = exchange.getRequest();
			// Check is Authorization valure preent in header
			boolean isAuth = request.getHeaders().containsKey(jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION);
			
			if(!isAuth) {
				onError(exchange,"Authorization not present" , HttpStatus.UNAUTHORIZED);
			}
			
			String headerAutthorization = request.getHeaders().get(jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION).get(0);
			String jwt = headerAutthorization.replace("Bearer ", "");
			
			if(!isValidToken(jwt)){
				return onError(exchange, "Jwt is not Valid", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus unauthorized) {
		ServerHttpResponse resp =	exchange.getResponse();
		resp.setStatusCode(unauthorized);
		return resp.setComplete();
	}
	
	
	// Parse Token
	private boolean isValidToken(String jwt) {
		boolean isTokenValid = true;
		
		// Get secret Token
		String secretToken = env.getProperty("token.secret");
		// Get byte from encoode Base64
		byte[] secreTokenbytes = java.util.Base64.getEncoder().encode(secretToken.getBytes());
		// Get key by algorithm on secret Token
		SecretKeySpec secretKey = new SecretKeySpec(secreTokenbytes,Keys.hmacShaKeyFor(secreTokenbytes).getAlgorithm());
		
		// Get Parser by secret Key 
		JwtParser jwtParser = Jwts.parser()
			.verifyWith(secretKey)
			.build();
		
	
		try {
			jwtParser.parse(jwt);
		} catch (Exception e) {
			isTokenValid = false;
		}
		
		

		return isTokenValid;
	}
}
