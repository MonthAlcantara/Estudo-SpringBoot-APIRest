package io.github.monthalcantara.mercadolivre.controller;

import io.github.monthalcantara.mercadolivre.dto.request.NovoUsuarioRequest;
import io.github.monthalcantara.mercadolivre.dto.response.UsuarioResponse;
import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Usuario usuario = converteUsuarioRequestEmUsuario(usuarioRequest);
        usuarioRepository.save(usuario);

        URI uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private Usuario converteUsuarioRequestEmUsuario(@Valid NovoUsuarioRequest usuarioRequest) {
        return usuarioRequest.toModel();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity buscaUsuarioPeloId(@PathVariable("id") UUID id) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        Usuario usuario = usuarioOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    @GetMapping("/login/{login}")
    @Transactional
    public ResponseEntity buscaUsuarioPeloLogin(@PathVariable("login") String login, @PageableDefault(size = 5) Pageable pageable) {
        List<Usuario> usuarios = usuarioRepository.buscaPorLoginQueContem(login, pageable).getContent();

        verificaSeListaEstaVazia(usuarios);

        return ResponseEntity.ok(converteListaUsuarioEmPageResponse(usuarios));
    }

    private PageImpl<UsuarioResponse> converteListaUsuarioEmPageResponse(List<Usuario> usuarios) {
        List<UsuarioResponse> usuarioResponseList = converteListaUsuariosEmListaResponse(usuarios);
        return new PageImpl<>(usuarioResponseList);
    }

    private void verificaSeListaEstaVazia(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }
    }

    private List<UsuarioResponse> converteListaUsuariosEmListaResponse(List<Usuario> usuarios) {
        return usuarios
                .stream()
                .map(usuario -> new UsuarioResponse(usuario))
                .collect(Collectors.toList());
    }
}
