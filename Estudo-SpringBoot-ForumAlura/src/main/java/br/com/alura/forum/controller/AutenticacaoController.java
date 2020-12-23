package br.com.alura.forum.controller;

import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AutenticacaoController {

    /*
    * Esse endpoint será chamado pelo cliente passando login e senha
    * La na tela de login do front end quando passarem o login e a senha
    * esse endpoint é que receberá os dados num DTO que criei e chamei de
    * LoginForm para autenticar e gerar o token
    * */
    @PostMapping("/api/auth")
    public ResponseEntity autentica(@RequestBody @Valid LoginForm loginForm){
return ResponseEntity.ok().build();
    }
}
