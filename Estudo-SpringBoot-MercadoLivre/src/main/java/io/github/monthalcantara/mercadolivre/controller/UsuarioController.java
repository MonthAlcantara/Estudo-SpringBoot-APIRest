package io.github.monthalcantara.mercadolivre.controller;

import io.github.monthalcantara.mercadolivre.dto.request.NovoUsuarioRequest;
import io.github.monthalcantara.mercadolivre.dto.response.UsuarioResponse;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity criaNovoUsuario(@Valid @RequestBody NovoUsuarioRequest usuarioRequest, UriComponentsBuilder builder) {
        Usuario usuario = usuarioRequest.toModel();
        usuarioRepository.save(usuario);

        URI uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity buscaUsuarioPeloId(@PathVariable("id") UUID id) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        Usuario usuario = usuarioOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }
}
