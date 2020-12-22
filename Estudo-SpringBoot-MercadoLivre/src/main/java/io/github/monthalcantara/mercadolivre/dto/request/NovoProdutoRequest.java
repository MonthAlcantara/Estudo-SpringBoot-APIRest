package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.model.Categoria;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.validators.ExistsValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class NovoProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Positive
    private int quantidade;

    @Size(min = 3)
    private List<String> caracteristicas;

    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistsValue(atributo = "id", classe = Categoria.class)
    private UUID categoriaId;

    @Deprecated
    private NovoProdutoRequest() {
    }

    public NovoProdutoRequest(@NotBlank String nome,
                              @NotNull @Positive BigDecimal valor,
                              @NotNull @Positive int quantidade,
                              @Min(3) List<String> caracteristicas,
                              @Size(min = 1000) String descricao,
                              @NotNull UUID categoriaId) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
    }

    public Produto toModel(EntityManager manager) {
        Categoria categoria = buscaCategoriaNoBanco(manager);
        verificaSeExisteCategoria(categoria);
        return new Produto(this.nome, this.valor, this.quantidade, this.caracteristicas, this.descricao, categoria);
    }

    private Categoria buscaCategoriaNoBanco(EntityManager manager) {
        return manager.find(Categoria.class, this.categoriaId);
    }

    private void verificaSeExisteCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível cadastrar esse produto, verifique");
        }
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

    public UUID getCategoriaId() {
        return categoriaId;
    }
}
