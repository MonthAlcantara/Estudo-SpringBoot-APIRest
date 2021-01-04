package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.model.Opiniao;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
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

    public Opiniao toModel() {
          return new Opiniao(this.nota, this.titulo, this.descricao);
    }

// public Opiniao toModel(EntityManager manager, Integer id) {
////        Produto produto = manager.find(Produto.class, id);
////        Assert.notNull(produto, "Para cadastrar a opiniao, um produto deve ser associado");
//
//        Opiniao opiniao = new Opiniao(this.nota, this.titulo, this.descricao);
//        //produto.adicionaOpiniao(opiniao);
//
//        return opiniao;
//    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}

