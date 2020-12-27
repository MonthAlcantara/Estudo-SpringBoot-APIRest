package io.github.monthalcantara.mercadolivre.controller.opiniao;

import io.github.monthalcantara.mercadolivre.dto.request.NovaOpiniaoRequest;
import io.github.monthalcantara.mercadolivre.model.Opiniao;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.repository.OpiniaoRepository;
import io.github.monthalcantara.mercadolivre.validators.ExistsValue;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/produtos")
public class OpiniaoController {

    private OpiniaoRepository opiniaoRepository;

    private EntityManager manager;

    public OpiniaoController(OpiniaoRepository opiniaoRepository, EntityManager manager) {
        this.opiniaoRepository = opiniaoRepository;
        this.manager = manager;
    }

    @PostMapping("/{id}/opinioes")
    @Transactional
    //TODO receber o id do produto pelo path, seguindo as boas praticas e implementar um usuario default cadastrando opiniao
    public ResponseEntity criaNovaOpiniao(@RequestBody @Valid NovaOpiniaoRequest opiniaoRequest,
                                          @PathVariable("id") @ExistsValue(classe = Produto.class, atributo = "id") Integer id,
                                          UriComponentsBuilder builder) {

        Opiniao opiniao = opiniaoRequest.toModel(manager, id);
        opiniaoRepository.save(opiniao);

        URI uri = builder.path("/produtos/{idProduto}/opinioes/{id}").buildAndExpand(id, opiniao.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{idProduto}/opinioes/{id}")
    public ResponseEntity buscaOpiniao(@PathVariable(value = "idProduto") Integer idProduto, @PathVariable(value = "id") Integer idOpiniao) {
        List opiniao = manager
                .createQuery("Select o from Opiniao o where o.id =:idOpiniao")
                .setParameter("idOpiniao", idOpiniao)
                .getResultList();
        return ResponseEntity.ok(opiniao);
    }
}
