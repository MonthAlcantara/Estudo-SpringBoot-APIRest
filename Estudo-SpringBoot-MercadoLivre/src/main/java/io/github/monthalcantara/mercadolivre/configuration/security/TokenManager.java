package io.github.monthalcantara.mercadolivre.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationInMillis;

    /*
     * Gero um Token em função do Authentication
     * */
    public String generateToken(Authentication authentication) {

        /*
         * faço o cast pra UserDetails
         * */
        UserDetails user = (UserDetails) authentication.getPrincipal();

        final Date now = new Date();
        /*
        * insiro a expiração setada no application properties
        * */
        final Date expiration = new Date(now.getTime() + this.expirationInMillis);

        /*
         * Gero meu token JWT e é esse token que eu retorno no UserAuthenticationController
         * */
        return Jwts.builder()
                .setIssuer("Desafio jornada dev eficiente mercado livre")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public boolean isValid(String jwt) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserName(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }
}
