

package com.pshs.attendance_system.security;

import com.pshs.attendance_system.models.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration-time}")
	private long jwtExpiration;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(User user) {
		return generateToken(new HashMap<>(), user);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
		return buildToken(extraClaims, user, jwtExpiration);
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails user, long expiration) {
		return Jwts.builder()
			.claims(extraClaims)
			.subject(user.getUsername())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(getSignInKey())
			.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	public boolean isTokenValid(String token, String username) {
		return (username.equals(extractUsername(token))) && !isTokenExpired(token);
	}

	public boolean isTokenValidAndNotExpired(String token, String username) {
		final String extractedUsername = extractUsername(token);
		boolean isExpired = isTokenExpired(token);

		return (extractedUsername.equals(username)) && !isExpired;
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}