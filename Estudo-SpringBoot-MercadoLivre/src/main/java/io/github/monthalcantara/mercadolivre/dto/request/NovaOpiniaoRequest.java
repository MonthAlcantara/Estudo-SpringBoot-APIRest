package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.model.Opiniao;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovaOpiniaoRequest {

    @Positive
    @Max(5)
    private int nota;

    @NotBlank
    @Length(max = 500)
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private Integer usuarioId;

    @Deprecated
    public NovaOpiniaoRequest() {
    }

    public NovaOpiniaoRequest(@Positive @Max(5) int nota,
                              @NotBlank @Length(max = 500) String titulo,
                              @NotBlank String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao toModel(Usuario usuario) {
          return new Opiniao(this.nota, this.titulo, this.descricao, usuario);
    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }
}

