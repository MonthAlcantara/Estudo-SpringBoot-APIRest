package br.com.alura.forum.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    public LoginForm(@NotBlank String email, @NotBlank String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }
}
