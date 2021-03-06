package io.github.monthalcantara.mercadolivre.dto.request;

import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.model.CaracteristicaProduto;
import io.github.monthalcantara.mercadolivre.model.Categoria;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.validators.ExistsValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Valid
    private List<CaracteristicaProdutoRequest> caracteristicas = new ArrayList<>();

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
                              @Min(3) List<CaracteristicaProdutoRequest> caracteristicas,
                              @Size(min = 1000) String descricao,
                              @NotNull UUID categoriaId) {

        Assert.isTrue(caracteristicas.size() >= 3, "O produto precisa ter no mínimo 3 caracteristicas para ser cadastrado");
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

        List<CaracteristicaProduto> caracteristicasProdutos = toListModel(this.caracteristicas);

        Assert.isTrue(caracteristicasProdutos.size() >= 3, "O produto precisa ter no mínimo 3 caracteristicas para ser cadastrado");
        return new Produto(this.nome, this.valor, this.quantidade, caracteristicasProdutos, this.descricao, categoria);
    }

    private List<CaracteristicaProduto> toListModel(List<CaracteristicaProdutoRequest> caracteristicas) {
        Assert.isTrue(!caracteristicas.isEmpty(), "O produto precisa ter no mínimo 3 caracteristicas para ser cadastrado");
        return caracteristicas.stream().map(caracteristicaProdutoRequest -> caracteristicaProdutoRequest.toModel()).collect(Collectors.toList());
    }

    private Categoria buscaCategoriaNoBanco(EntityManager manager) {
        return manager.find(Categoria.class, this.categoriaId);
    }

    private void verificaSeExisteCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível cadastrar esse produto, verifique");
        }
    }

    public Set<String> temCaracteristicaRepetida() {
        Set<String> caracteristicasRepetidas = new HashSet<>();
        Set<String> nomesIguais = new HashSet<>();

        for(CaracteristicaProdutoRequest caracteristica : this.caracteristicas){
            if(!nomesIguais.add(caracteristica.getNome())){
                caracteristicasRepetidas.add(caracteristica.getNome());
            }
        }
        return caracteristicasRepetidas;
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

    public List<CaracteristicaProdutoRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public UUID getCategoriaId() {
        return categoriaId;
    }
}
