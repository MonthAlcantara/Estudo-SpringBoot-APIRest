package io.github.monthalcantara.mercadolivre.dto.response;

import io.github.monthalcantara.mercadolivre.model.Pergunta;

public class PerguntaResponse {


    private String titulo;
    private String texto;

    @Deprecated
    public PerguntaResponse() {
    }


    public PerguntaResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.texto = pergunta.getTexto();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }
}
