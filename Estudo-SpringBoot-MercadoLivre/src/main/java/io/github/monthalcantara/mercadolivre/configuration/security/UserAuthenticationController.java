package io.github.monthalcantara.mercadolivre.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Profile(value = {"test","prod"})
public class UserAuthenticationController {

    /*
    * Esse AuthenticationManager eu expus como um bean la no meu
    * SecurityConfiguration
    * */
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenManager tokenManager;

    private static final Logger log = LoggerFactory
            .getLogger(UserAuthenticationController.class);


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    /*
    * Nesse endpoint eu recebo um LoginInputDto
    * */
    public ResponseEntity<String> authenticate(@RequestBody LoginInputDto loginInfo) {
        /*Para fazer a autenticação eu preciso passar um objeto do tipo UsernamePasswordAuthenticationToken
         * Iae eu preciso converter esse login que chega nesse objeto. Fiz como sempre faço com o
         * toModel() só que chamei de build() aqui
         */
        UsernamePasswordAuthenticationToken authenticationToken = loginInfo.build();

        try {
            /*
             * Quando chegar nessa linha o Spring sabe que deve chamar o AutenticacaoService
             * */
            Authentication authentication = authManager.authenticate(authenticationToken);
            /*
            iae para devolver o token eu vou usar a lib do jjwt que coloquei no projeto
            iae pra não deixar o código da biblioteca solta no controller, é boa pratica criar um
            Service para concentrar esse código de criação do token
            */
            String jwt = tokenManager.generateToken(authentication);

            return ResponseEntity.ok(jwt);

        } catch (AuthenticationException e) {
            log.error("[Autenticacao] {}",e);
            return ResponseEntity.badRequest().build();
        }

    }
}
