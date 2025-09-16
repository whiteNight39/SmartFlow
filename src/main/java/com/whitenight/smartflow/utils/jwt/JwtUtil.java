package com.whitenight.smartflow.utils.jwt;

import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final StaffRepository staffRepository;
    private final String secretKey;
    private final Key key;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;


    public JwtUtil(StaffRepository staffRepository, @Value("${SECRET_KEY}") String secretKey) {
        this.staffRepository = staffRepository;
        this.secretKey = secretKey;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UUID staffId) {

        Staff staff = staffRepository.getStaffById(staffId);
        String staffAuthLevel = staff.getStaffRole();

        return Jwts.builder()
                .setSubject(String.valueOf(staffId))
                .claim("staffRole", staff.getStaffRole())
                .claim("staffCompanyId", staff.getStaffCompanyId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID extractUserId(String token) {

        return UUID.fromString(Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Date getExpirationDate(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Date getIssueDate(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt();
    }
}
