package io.github.monthalcantara.mercadolivre.dto.response;

import io.github.monthalcantara.mercadolivre.model.CaracteristicaProduto;

public class CaracteristicaProdutoResponse {


    private String nome;

    private String descricao;


    public CaracteristicaProdutoResponse(CaracteristicaProduto caracteristicaProduto) {
        this.nome = caracteristicaProduto.getNome();
        this.descricao = caracteristicaProduto.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
