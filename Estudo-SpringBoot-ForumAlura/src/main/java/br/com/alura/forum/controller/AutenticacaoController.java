package br.com.alura.forum.controller;

import br.com.alura.forum.config.security.TokenManager;
import br.com.alura.forum.controller.dto.TokenDTO;
import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    /*
     * Para fazer uam autenticação manualmente eu preciso de uma classe fornecida pelo Spring
     * Porém embora seja fornecida pelo Spring, o Spring não consegue injetar essa classe
     * Eu preciso fazer isso na minha classe de configuração e a classe a qual a minha config herda
     * WebSecurityConfigurerAdapter, tem um método que faz isso authenticationManager eu só preciso Sobrescreve-la
     * e anotar ela como o @Bean
     * */
    private AuthenticationManager authenticationManager;
    private TokenManager tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenManager tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /*
     * Esse endpoint será chamado pelo cliente passando login e senha
     * La na tela de login do front end quando passarem o login e a senha
     * esse endpoint é que receberá os dados num DTO que criei e chamei de
     * LoginForm para autenticar e gerar o token
     * */
    @PostMapping
    public ResponseEntity autentica(@RequestBody @Valid LoginForm loginForm) {

        /*Para fazer a autenticação eu preciso passar um objeto do tipoUsernamePasswordAuthenticationToken
         * Iae eu preciso converter esse login que chega nesse objeto. Fiz como sempre faço com o
         * toModel()
         */
        UsernamePasswordAuthenticationToken dadosLogin = loginForm.toUsernamePasswordAuthenticationToken();
        try {
            /*
             * Quando chegar nessa linha o Spring sabe que deve chamar o AutenticacaoService
             * */
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            /*
            iae para devolver o token eu vou usar a lib do jjwt que coloquei no projeto
            iae pra não deixar o código da biblioteca solta no controller, é boa pratica criar um
            Service para concentrar esse código de criação do token
            */
            String token = tokenService.gerarToken(authentication);

            /*
            * Para não devolver o token simplesmente, eu criei uma classe DTO para devolver esse
            * token ao cliente e nele informo o token + o método que será utilizado basic, Bearer...
            * */
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
