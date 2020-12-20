package io.github.monthalcantara.mercadolivre.controller.usuario;

import io.github.monthalcantara.mercadolivre.dto.response.UsuarioResponse;
import io.github.monthalcantara.mercadolivre.exception.ApiErrorException;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import io.github.monthalcantara.mercadolivre.repository.UsuarioRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class BuscaUsuarioController {

    private UsuarioRepository usuarioRepository;

    public BuscaUsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity buscaUsuarioPeloId(@PathVariable("id") UUID id) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        Usuario usuario = verificaSeFoiEncontradoUsuario(usuarioOptional);

        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    private Usuario verificaSeFoiEncontradoUsuario(Optional<Usuario> usuarioOptional) {
        return usuarioOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
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
