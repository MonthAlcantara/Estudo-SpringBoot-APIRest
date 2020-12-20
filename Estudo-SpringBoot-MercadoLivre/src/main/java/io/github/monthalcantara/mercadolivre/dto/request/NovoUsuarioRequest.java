package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.validators.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovoUsuarioRequest {

    @NotBlank
    @Email
    @UniqueValue(atributo = "login", classe = Usuario.class)
    private String login;

    @NotBlank
    @Length(min = 6)
    private String senha;


    public Usuario toModel() {
        return new Usuario(this.login, this.senha);
    }

    public NovoUsuarioRequest(@NotBlank @Email String login, @NotBlank String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
