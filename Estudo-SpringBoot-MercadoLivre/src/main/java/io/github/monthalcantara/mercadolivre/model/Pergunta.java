package io.github.monthalcantara.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column()
    private String titulo;

    @ManyToOne
    private Usuario usuario;

    private LocalDateTime instanteCriacao;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(@NotBlank String titulo, @NotNull Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
        this.instanteCriacao = LocalDateTime.now();
    }
}
