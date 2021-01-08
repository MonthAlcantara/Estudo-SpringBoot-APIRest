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
    private String titulo;

    @NotBlank
    private String texto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime instanteCriacao;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(@NotBlank String titulo, @NotBlank String texto, @NotNull Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.texto = texto;
        this.usuario = usuario;
        this.produto = produto;
        this.instanteCriacao = LocalDateTime.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }

    public Produto getProduto() {
        return produto;
    }
}
