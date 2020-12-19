package io.github.monthalcantara.mercadolivre.controller;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario", indexes = {@Index(columnList = "login")})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Email
    private String login;

    @NotBlank
    @Size(min = 6)
    private String senha;

    private LocalDateTime instanteCriacao;

    @Deprecated
    public Usuario() {
    }

    public Usuario(@NotBlank @Email String login, @NotBlank @Size(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
        this.instanteCriacao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}
