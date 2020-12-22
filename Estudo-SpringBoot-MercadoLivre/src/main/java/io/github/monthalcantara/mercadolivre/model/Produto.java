package io.github.monthalcantara.mercadolivre.model;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "caracteristicas")
    private List<String> caracteristicas;

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
                   @Min(3) List<String> caracteristicas,
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

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
