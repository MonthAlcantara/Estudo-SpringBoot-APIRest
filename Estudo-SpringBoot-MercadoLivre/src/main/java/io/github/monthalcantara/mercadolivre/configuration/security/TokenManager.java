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
         * Eu preciso pegar o usuario daquela requisição que está logado e o Authentication
         * possui o método getPrincipal que me permite fazer isso. Devolve um object, por isso
         * o cast
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
                //Quem é o Issuer? quem foi a aplicação q fez a geração do token?
                .setIssuer("Desafio jornada dev eficiente mercado livre")
                //A quem esse token pertence? Quem é o usuario autenticado dono
                // desse token. Eu preciso passar algo que identifique o usuario
                .setSubject(user.getUsername())
                //Qual foi a data de geração do token? O jWT utiliza a biblioteca antiga de datas do java
                .setIssuedAt(now)
                /*Qual a data de expiração do token? Ele tbm espera uma Date. Só que aí ao invés de
                passar o horario diretamente aqui eu posso criar no application.properties e injetar no atributo expiration
                */
                .setExpiration(expiration)
                /*
                  Pela especificação do Token ele precisa ser criptografado então para isso eu uso o método
                  signWith onde eu informo o algotirimo de criptografia HS256 e o segredo que será usada para a codificação
                */
                .signWith(SignatureAlgorithm.HS256, this.secret)
                //Aqui eu compacto tudo e gero o token
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
