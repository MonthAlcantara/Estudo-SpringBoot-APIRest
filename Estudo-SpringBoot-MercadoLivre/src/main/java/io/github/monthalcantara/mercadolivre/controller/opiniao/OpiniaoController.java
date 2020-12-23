package io.github.monthalcantara.mercadolivre.controller.opiniao;

import io.github.monthalcantara.mercadolivre.dto.request.NovaOpiniaoRequest;
import io.github.monthalcantara.mercadolivre.model.Opiniao;
import io.github.monthalcantara.mercadolivre.repository.OpiniaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/opinioes")
public class OpiniaoController {

    private OpiniaoRepository opiniaoRepository;

    private EntityManager manager;

    public OpiniaoController(OpiniaoRepository opiniaoRepository, EntityManager manager) {
        this.opiniaoRepository = opiniaoRepository;
        this.manager = manager;
    }

    @PostMapping
    @Transactional
    //TODO receber o id do produto pelo path, seguindo as boas praticas e implementar um usuario default cadastrando opiniao
    public ResponseEntity criaNovaOpiniao(@RequestBody @Valid NovaOpiniaoRequest opiniaoRequest, UriComponentsBuilder builder) {

        Opiniao opiniao = opiniaoRequest.toModel(manager);
        opiniaoRepository.save(opiniao);

        URI uri = builder.path("/opinioes/{id}").buildAndExpand(opiniao.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
