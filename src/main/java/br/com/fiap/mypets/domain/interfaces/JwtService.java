package br.com.fiap.mypets.domain.interfaces;

import br.com.fiap.mypets.domain.model.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(User userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
