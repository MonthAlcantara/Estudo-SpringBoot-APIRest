package io.github.monthalcantara.mercadolivre.controller.pergunta;

import io.github.monthalcantara.mercadolivre.dto.request.PerguntaRequest;
import io.github.monthalcantara.mercadolivre.dto.response.PerguntaResponse;
import io.github.monthalcantara.mercadolivre.model.Pergunta;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.PerguntaRepository;
import io.github.monthalcantara.mercadolivre.repository.ProdutoRepository;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class NovaPerguntaController {

    private ProdutoRepository produtoRepository;
    private PerguntaRepository perguntaRepository;
    private UsuarioRepository usuRepository;

    public NovaPerguntaController(ProdutoRepository produtoRepository, PerguntaRepository perguntaRepository, UsuarioRepository usuRepository) {
        this.produtoRepository = produtoRepository;
        this.perguntaRepository = perguntaRepository;
        this.usuRepository = usuRepository;
    }

    @PostMapping("/{id}/perguntas")
    @Transactional
    public ResponseEntity criaNovaPergunta(@PathVariable("id") Integer id, @RequestBody @Valid PerguntaRequest request, Authentication auth) {
        Usuario usuarioLogado = usuRepository.findByLogin("aluno@email.com").get();

        Produto produto = buscaProdutoPeloId(id);

        Pergunta pergunta = request.toModel(produto, usuarioLogado);
        perguntaRepository.save(pergunta);

        Set<PerguntaResponse> perguntasResponse = produto.getPerguntas().stream().map(PerguntaResponse::new).collect(Collectors.toSet());
        return new ResponseEntity(perguntasResponse, HttpStatus.CREATED);
    }

    private Produto buscaProdutoPeloId(@PathVariable("id") Integer id) {
        return produtoRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Por favor verifique se o Produto existe")
                );
    }
}
