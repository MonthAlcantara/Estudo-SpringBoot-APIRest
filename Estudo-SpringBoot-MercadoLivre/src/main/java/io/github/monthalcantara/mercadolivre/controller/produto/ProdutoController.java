package io.github.monthalcantara.mercadolivre.controller.produto;

import io.github.monthalcantara.mercadolivre.dto.request.NovoProdutoRequest;
import io.github.monthalcantara.mercadolivre.dto.response.ProdutoResponse;
import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    private EntityManager manager;

    public ProdutoController(ProdutoRepository produtoRepository, EntityManager manager) {
        this.produtoRepository = produtoRepository;
        this.manager = manager;
    }


    @PostMapping
    @Transactional
    public ResponseEntity criaNovoProduto(@RequestBody @Valid NovoProdutoRequest produtoRequest, UriComponentsBuilder builder) {
        Produto produto = produtoRequest.toModel(manager);
        produtoRepository.save(produto);

        URI uri = geraUriNovoRecurso("/produtos/{id}", builder, produto);
        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }

    private URI geraUriNovoRecurso(String caminho, UriComponentsBuilder builder, Produto produto) {
        return builder.path(caminho).buildAndExpand(produto.getId()).toUri();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity buscaProdutoPeloId(@PathVariable("id") Integer id) {
        Produto produto = buscaProdutoNoBancoSeExistir(id);
        return ResponseEntity.ok(produto);
    }

    private Produto buscaProdutoNoBancoSeExistir(@PathVariable("id") Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(
                        () -> new ApiErrorException(HttpStatus.NOT_FOUND, "Não foi encontrado produto com o Id informado")
                );
    }
}
