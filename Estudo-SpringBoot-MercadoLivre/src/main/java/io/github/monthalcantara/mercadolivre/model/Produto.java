package io.github.monthalcantara.mercadolivre.model;

import io.github.monthalcantara.mercadolivre.dto.request.CaracteristicaProdutoRequest;
import io.github.monthalcantara.mercadolivre.dto.response.CaracteristicaProdutoResponse;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(indexes = {@Index(name = "nome", columnList = "nome")})
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Positive
    private int quantidade;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private List<CaracteristicaProduto> caracteristicas = new ArrayList<>();

    @Size(max = 1000)
    @Column(length = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    private LocalDateTime instanteCriacao;

    @Deprecated
    private Produto() {
    }

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @Positive int quantidade,
                   @Min(3) List<CaracteristicaProduto> caracteristicas,
                   @Size(max = 1000) String descricao,
                   @NotNull Categoria categoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoria = categoria;
        this.instanteCriacao = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
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

    public List<CaracteristicaProduto> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<CaracteristicaProdutoResponse> toListCaracteristicasResponse() {
     return this.caracteristicas.stream().map(CaracteristicaProdutoResponse::new).collect(Collectors.toList());
    }
}
