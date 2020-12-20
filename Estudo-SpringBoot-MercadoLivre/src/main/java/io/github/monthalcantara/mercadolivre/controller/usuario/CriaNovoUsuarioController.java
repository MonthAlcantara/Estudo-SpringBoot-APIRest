package io.github.monthalcantara.mercadolivre.controller.usuario;

import io.github.monthalcantara.mercadolivre.dto.request.NovoUsuarioRequest;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class CriaNovoUsuarioController {

    private UsuarioRepository usuarioRepository;

    public CriaNovoUsuarioController(UsuarioRepository usuarioRepository) {
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


}
