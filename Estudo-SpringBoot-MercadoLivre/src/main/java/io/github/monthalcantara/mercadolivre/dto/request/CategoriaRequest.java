package io.github.monthalcantara.mercadolivre.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.monthalcantara.mercadolivre.model.Categoria;
import io.github.monthalcantara.mercadolivre.validators.ExistsValue;
import io.github.monthalcantara.mercadolivre.validators.UniqueValue;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(classe = Categoria.class, atributo = "nome")
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ExistsValue(classe = Categoria.class, atributo = "id")
    private UUID idCategoriaMae;

    @Deprecated
    public CategoriaRequest() {
    }

    public CategoriaRequest(@NotBlank String nome, UUID idCategoriaMae) {
        this.nome = nome;
        this.idCategoriaMae = idCategoriaMae;
    }

    public Categoria toModel() {
    return new Categoria(this.nome);
    }

    public UUID getIdCategoriaMae() {
        return idCategoriaMae;
    }

    public String getNome() {
        return nome;
    }

}
