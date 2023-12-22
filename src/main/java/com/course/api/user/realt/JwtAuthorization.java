package com.course.api.user.realt;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

public class JwtAuthorization {
	
	Jwt<?, ?> jwtObject;
	
	public JwtAuthorization(String jwtString, String secretToken) {
	this.jwtObject = parseJwt(jwtString,secretToken);
	}

	
	private Jwt<?, ?> parseJwt(String jwtObjbet, String secretToken) {
		byte[] secretTokenBytes = Base64.getEncoder().encode(secretToken.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretTokenBytes, SIG.HS512.toString());
		JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

		return jwtParser.parse(jwtObjbet);
	}

	public Collection<GrantedAuthority> getAuhorities() {
		Collection<Map<String, String>> grantedAuthorities = ((Claims) jwtObject.getPayload()).get("scope", List.class);
		return grantedAuthorities.stream().map(scope -> new SimpleGrantedAuthority(scope.get("authority")))
				.collect(Collectors.toList());
		
	}

	public String getSubject() {
		return ((Claims) jwtObject.getPayload()).getSubject();

	}
}

