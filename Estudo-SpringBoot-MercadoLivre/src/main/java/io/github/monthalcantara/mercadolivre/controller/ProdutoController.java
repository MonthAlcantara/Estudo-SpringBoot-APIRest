package io.github.monthalcantara.mercadolivre.controller;

import io.github.monthalcantara.mercadolivre.dto.request.NovoProdutoRequest;
import io.github.monthalcantara.mercadolivre.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity criaNovoProduto(@RequestBody @Valid NovoProdutoRequest produtoRequest, UriComponentsBuilder builder){


        return ResponseEntity.ok().build();

    }
}
