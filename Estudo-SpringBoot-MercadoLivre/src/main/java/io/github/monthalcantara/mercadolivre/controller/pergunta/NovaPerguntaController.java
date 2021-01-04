package io.github.monthalcantara.mercadolivre.controller.pergunta;

import io.github.monthalcantara.mercadolivre.dto.request.PerguntaRequest;
import io.github.monthalcantara.mercadolivre.model.Pergunta;
import io.github.monthalcantara.mercadolivre.model.Produto;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.ProdutoRepository;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class NovaPerguntaController {

    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuRepository;

    public NovaPerguntaController(ProdutoRepository produtoRepository, UsuarioRepository usuRepository) {
        this.produtoRepository = produtoRepository;
        this.usuRepository = usuRepository;
    }

    @PostMapping("/{id}/perguntas")
    public ResponseEntity criaNovaPergunta(@PathVariable("id") Integer id, @RequestBody @Valid PerguntaRequest request, Authentication auth, UriComponentsBuilder builder) {
        Usuario usuarioLogado = usuRepository.findByLogin("aluno@email.com").get();

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Por favor verifique se o Produto existe"));

        Pergunta pergunta = request.toModel(produto, usuarioLogado);

        produto.adicionaPergunta(pergunta);

        produtoRepository.save(produto);

        return ResponseEntity.ok(produto.getPerguntas());
    }
}
