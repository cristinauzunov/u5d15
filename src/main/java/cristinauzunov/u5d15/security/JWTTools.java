package cristinauzunov.u5d15.security;

import cristinauzunov.u5d15.entity.Utente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String segreto;

    // crea un token per l'utente che ha appena fatto il login
    public String generaToken(Utente utente) {
        SecretKey chiave = Keys.hmacShaKeyFor(segreto.getBytes());
        Date adesso = new Date();
        Date scadenza = new Date(adesso.getTime() + 1000 * 60 * 60); // 1 ora
        return Jwts.builder()
                .subject(String.valueOf(utente.getId()))
                .issuedAt(adesso)
                .expiration(scadenza)
                .signWith(chiave)
                .compact();
    }

    // controlla che il token sia valido: firma giusta e non scaduto
    public void verificaToken(String token) {
        SecretKey chiave = Keys.hmacShaKeyFor(segreto.getBytes());
        Jwts.parser()
                .verifyWith(chiave)
                .build()
                .parseSignedClaims(token);
    }

    // estrae l'id dell'utente che era stato salvato dentro il token
    public Long estraiId(String token) {
        SecretKey chiave = Keys.hmacShaKeyFor(segreto.getBytes());
        String id = Jwts.parser()
                .verifyWith(chiave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return Long.parseLong(id);
    }
}