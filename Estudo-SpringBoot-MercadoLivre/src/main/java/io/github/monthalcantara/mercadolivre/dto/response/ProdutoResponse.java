package io.github.monthalcantara.mercadolivre.dto.response;

import io.github.monthalcantara.mercadolivre.model.Produto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoResponse {

    private String nome;

    private BigDecimal valor;

    private int quantidade;

    private List<String> caracteristicas;

    private String descricao;

    private CategoriaResponse categoria;

    @Deprecated
    private ProdutoResponse() {
    }

    public ProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.quantidade = produto.getQuantidade();
        this.caracteristicas = produto.getCaracteristicas();
        this.descricao = produto.getDescricao();
        this.categoria = new CategoriaResponse(produto.getCategoria());
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaResponse getCategoria() {
        return categoria;
    }
}
