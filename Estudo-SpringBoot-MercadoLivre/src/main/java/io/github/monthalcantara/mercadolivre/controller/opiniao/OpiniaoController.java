package io.github.monthalcantara.mercadolivre.controller.opiniao;

import io.github.monthalcantara.mercadolivre.compartilhado.UsuarioLogado;
import io.github.monthalcantara.mercadolivre.dto.request.NovaOpiniaoRequest;
import io.github.monthalcantara.mercadolivre.dto.response.OpiniaoResponse;
import io.github.monthalcantara.mercadolivre.model.Opiniao;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.OpiniaoRepository;
import io.github.monthalcantara.mercadolivre.repository.ProdutoRepository;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import io.github.monthalcantara.mercadolivre.validators.ExistsValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/produtos")
public class OpiniaoController {

    private OpiniaoRepository opiniaoRepository;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;

    private EntityManager manager;

    public OpiniaoController(OpiniaoRepository opiniaoRepository, ProdutoRepository produtoRepository, EntityManager manager) {
        this.opiniaoRepository = opiniaoRepository;
        this.produtoRepository = produtoRepository;
        this.manager = manager;
    }

    @PostMapping("/{id}/opinioes")
    @Transactional
    public ResponseEntity criaNovaOpiniao(@RequestBody @Valid NovaOpiniaoRequest opiniaoRequest,
                                          @PathVariable("id") @ExistsValue(classe = Produto.class, atributo = "id") Integer id,
                                          UriComponentsBuilder builder, Authentication authentication) {

        UsuarioLogado usulogado = (UsuarioLogado) authentication.getPrincipal();

        Usuario usuario = usulogado.getUsuario();
        Opiniao opiniao = opiniaoRequest.toModel(usuario);

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uma opinião deve estar vinculada a um produto"));
        produto.adicionaOpiniao(opiniao);
        produtoRepository.save(produto);
        URI uri = builder.path("/produtos/{idProduto}/opinioes/{id}").buildAndExpand(id, opiniao.getId()).toUri();

        return ResponseEntity.created(uri).body(new OpiniaoResponse(opiniao));
    }

    @GetMapping("/{idProduto}/opinioes/{id}")
    public ResponseEntity buscaOpiniao(@PathVariable(value = "idProduto") Integer idProduto, @PathVariable(value = "id") Integer idOpiniao) {
        Opiniao opiniao = opiniaoRepository.findById(idOpiniao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado opinião com os dados informados"));
        return ResponseEntity.ok(opiniao);
    }
}
