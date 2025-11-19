package com.mh.crj.utility;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtilService {

	public String generateToken(String email) {

		HashMap<String, Object> claims = new HashMap<>();
		
		return createToken(claims,email);
	}

    // Create JWT token with claims, subject, issue date, expiry, and signature
	private String createToken(HashMap<String, Object> claims, String email) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 )) // for 1 hour
				.signWith(getSecKey(),SignatureAlgorithm.HS256)
				.compact();
	}

//				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 )) // for 1 min
	

    // Get the secret key for signing and validation (Base64 encoded string)
	private Key getSecKey() {
		byte[] keyBytes = Decoders.BASE64.decode("3273357638792F423F4528SJCDNKLCMCMCKCDMOD655368566D597133743677397A244326");
		return Keys.hmacShaKeyFor(keyBytes);
	}

	

    // Extract username (subject) from JWT
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
	}


    // Generic method to extract any claim using a lambda function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    

    // Parse the JWT and get all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    // Validate token - check username match and expiry
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
	
}
