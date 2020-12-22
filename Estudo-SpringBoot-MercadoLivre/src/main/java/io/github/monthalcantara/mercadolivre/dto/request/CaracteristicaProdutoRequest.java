package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.model.CaracteristicaProduto;

import java.util.Objects;

public class CaracteristicaProdutoRequest {

    private String nome;

    private String descricao;

    @Deprecated
    public CaracteristicaProdutoRequest() {
    }

    public CaracteristicaProdutoRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public CaracteristicaProduto toModel() {
        return new CaracteristicaProduto(this.nome, this.descricao);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaracteristicaProdutoRequest that = (CaracteristicaProdutoRequest) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
