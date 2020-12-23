package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.model.Opiniao;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.validators.ExistsValue;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
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
    @ExistsValue(classe = Produto.class, atributo = "id")
    private Integer idProduto;

    @Deprecated
    public NovaOpiniaoRequest() {
    }

    public NovaOpiniaoRequest(@Positive @Max(5) int nota,
                              @NotBlank @Length(max = 500) String titulo,
                              @NotBlank String descricao,
                              @NotNull Integer idProduto) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.idProduto = idProduto;
    }

    public Opiniao toModel(EntityManager manager){
        Produto produto = manager.find(Produto.class, idProduto);
        Assert.notNull(produto, "Para cadastrar a opiniao, um produto deve ser associado");
        return new Opiniao(this.nota, this.titulo, this.descricao,produto);
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

    public Integer getIdProduto() {
        return idProduto;
    }
}

