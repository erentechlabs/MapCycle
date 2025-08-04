package com.mapcycle.mapcycle.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTProvider {

    private String secretKey = "";

    // Constructor to generate a secret key
    public JWTProvider() {
        try {

            // Generate a new secret key using HmacSha256 algorithm
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSha256");


            SecretKey sk = keyGenerator.generateKey();

            // Encode the secret key to a Base64 string
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Generate a JWT token for the given username
    public String generateToken(String username) {


        Map<String, Object> claims = new HashMap<>();

        // Build and sign the JWT token
        return Jwts.builder()
                .claims() // Create a new Claims object
                .add(claims) // Add claims to the Claims object
                .subject(username) // Set the subject (username)
                .issuedAt(new Date(System.currentTimeMillis())) // Set the issued at date
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30)) // Set the expiration date (30 hours from now)
                .and()
                .signWith(getKey()) // Sign the token with the secret key
                .compact();
    }

    // Get the secret key for signing the JWT token
    private SecretKey getKey() {


        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        // Return the secret key
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the username from the JWT token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a specific claim from the JWT token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    // Extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validate the JWT token for the given user details
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    // Check if the JWT token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract the expiration date from the JWT token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
