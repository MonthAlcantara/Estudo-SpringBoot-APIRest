package io.github.monthalcantara.mercadolivre.dto.response;

import io.github.monthalcantara.mercadolivre.model.Categoria;

public class CategoriaResponse {

    private String nome;

    @Deprecated
    public CategoriaResponse() {
    }

    public CategoriaResponse(Categoria categoria) {
        this.nome = categoria.getNome();
    }

    public String getNome() {
        return nome;
    }
}
