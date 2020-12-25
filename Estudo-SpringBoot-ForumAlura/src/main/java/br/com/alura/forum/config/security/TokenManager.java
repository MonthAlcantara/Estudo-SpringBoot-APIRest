package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManager {

    @Value("${forum.jwt.expiration}")
    private String expiration;
    @Value("${forum.jwt.secret}")
    private String secret;

    /*
     * Aqui eu crio o token passo a passo com a lib do jjwt
     * */
    public String gerarToken(Authentication authentication) {
        /*
         * Eu preciso pegar o usuario daquela requisição que está logado e o Authentication
         * possui o método getPrincipal que me permite fazer isso. Devolve um object, por isso
         * o cast
         * */
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        Date hoje = new Date();
        /*
         * insiro a expiração setada no application properties
         * */
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts
                //Chamando o builder do JJWT
                .builder()
                //Quem é o Issuer? quem foi a aplicação q fez a geração do token?
                .setIssuer("API do Fórum da Alura")
                //A quem esse token pertence? Quem é o usuario autenticado dono
                // desse token. Eu preciso passar algo que identifique o usuario
                .setSubject(usuarioLogado.getId().toString())
                //Qual foi a data de geração do token? O jWT utiliza a biblioteca antiga de datas do java
                .setIssuedAt(hoje)
                /*Qual a data de expiração do token? Ele tbm espera uma Date. Só que aí ao invés de
                passar o horario diretamente aqui eu posso criar no application.properties e injetar no atributo expiration
                */
                .setExpiration(dataExpiracao)
                /*
                  Pela especificação do Token ele precisa ser criptografado então para isso eu uso o método
                  signWith onde eu informo o algotirimo de criptografia HS256 e o segredo que será usada para a codificação
                */
                .signWith(SignatureAlgorithm.HS256, secret)
                //Aqui eu compacto tudo e gero o token
                .compact();


    }
}
