package io.github.monthalcantara.mercadolivre.model;

import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "categoria", indexes = @Index(name = "nome", columnList = "nome", unique = true))
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    @Deprecated
    public Categoria() {
    }

    public Categoria(String nome) {
        Assert.hasText(nome, "Necessario fornecer um nome para criar uma Categoria");
        this.nome = nome;
    }


    public void setCategoriaMae(UUID idCategoriaMae, CategoriaRepository categoriaRepository) {
        verificaDadosDeEntradaAoSetarCategoriaMae(idCategoriaMae, categoriaRepository);
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoriaMae);
        this.categoriaMae = categoria
                .orElseThrow(() ->
                        new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Incosistencia nos dados. Categoria Mãe não encontrada")
                );
    }

    private void verificaDadosDeEntradaAoSetarCategoriaMae(UUID idCategoriaMae, CategoriaRepository categoriaRepository) {
        Assert.notNull(idCategoriaMae, "Necessario informar o id da Categoria Mãe");
        Assert.notNull(categoriaRepository, "Necessario fornacer uma instancia de Categoriarepository");
    }
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoriaMae() {
        return categoriaMae;
    }
}
