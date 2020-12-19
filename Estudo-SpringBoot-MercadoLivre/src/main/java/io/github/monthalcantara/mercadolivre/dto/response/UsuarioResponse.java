package io.github.monthalcantara.mercadolivre.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.monthalcantara.mercadolivre.controller.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioResponse {

    private UUID id;

    private String login;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime instanteCriacao;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.instanteCriacao = usuario.getInstanteCriacao();
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
