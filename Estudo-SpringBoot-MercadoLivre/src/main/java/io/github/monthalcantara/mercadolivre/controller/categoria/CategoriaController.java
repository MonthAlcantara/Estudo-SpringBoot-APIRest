package io.github.monthalcantara.mercadolivre.controller.categoria;

import io.github.monthalcantara.mercadolivre.dto.request.CategoriaRequest;
import io.github.monthalcantara.mercadolivre.dto.response.CategoriaResponse;
import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.model.Categoria;
import io.github.monthalcantara.mercadolivre.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity criaNovaCategoria(@RequestBody @Valid CategoriaRequest categoriaRequest, UriComponentsBuilder builder) {
        Categoria categoria = converteCategoriaRequestEmCategoria(categoriaRequest);
        repository.save(categoria);
        URI uri = builder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private Categoria converteCategoriaRequestEmCategoria(@Valid CategoriaRequest categoriaRequest) {
        Categoria categoria = categoriaRequest.toModel();

        if (verficaSeCategoriaTemMae(categoriaRequest)) {
            categoria.setCategoriaMae(categoriaRequest.getIdCategoriaMae(), repository);
        }

        return categoria;
    }

    private boolean verficaSeCategoriaTemMae(@Valid CategoriaRequest categoriaRequest) {
        return categoriaRequest.getIdCategoriaMae() != null;
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity buscaCategoriaPeloId(@PathVariable("id") UUID id) {
        Categoria categoria = repository.findById(id).orElseThrow(() -> new ApiErrorException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        return ResponseEntity.ok(new CategoriaResponse(categoria));
    }
}
