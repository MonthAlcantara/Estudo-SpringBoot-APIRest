package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.model.Pergunta;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.model.Usuario;

import javax.validation.constraints.NotBlank;

public class PerguntaRequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String texto;

    @Deprecated
    public PerguntaRequest() {
    }

    public Pergunta toModel(Produto produto, Usuario usuario) {
        return new Pergunta(this.titulo, this.texto, usuario, produto);
    }

    public PerguntaRequest(@NotBlank String titulo, @NotBlank String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }
}
