package io.github.monthalcantara.mercadolivre.repository;

import io.github.monthalcantara.mercadolivre.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Pageable pageable = PageRequest.of(0, 5);


    @Test
    @DisplayName("Deveria buscar login que contem o valor passado")
    @Order(1)
    public void buscaPorLoginQueContemTest() {
        Usuario usuario = criaUsuarioAserSalvo();
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Page<Usuario> usuarioPage = usuarioRepository.buscaPorLoginQueContem("test", pageable);
        System.out.println(usuarioPage.getTotalElements());
        Assertions.assertNotNull(usuarioPage.getContent());
        Assertions.assertFalse(usuarioPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Deveria buscar pelo login completo")
    @Order(2)
    public void findByLoginTest() {
        Usuario usuario = criaUsuarioAserSalvo();
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Optional<Usuario> optionalUsuario = usuarioRepository.findByLogin("teste@teste.com.br");

        Assertions.assertTrue(optionalUsuario.isPresent());
       Assertions.assertEquals(optionalUsuario.get().getLogin(), usuario.getLogin());

    }

    @Test
    @DisplayName("Deveria retornar que existe login no banco")
    @Order(3)
    public void existsUsuarioByLoginTest() {
        Usuario usuario = criaUsuarioAserSalvo();
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Boolean optionalUsuario = usuarioRepository.existsUsuarioByLogin("teste@teste.com.br");

        Assertions.assertTrue(optionalUsuario);

    }


    private Usuario criaUsuarioAserSalvo() {
        return new Usuario("teste@teste.com.br", "1234567");
    }
}