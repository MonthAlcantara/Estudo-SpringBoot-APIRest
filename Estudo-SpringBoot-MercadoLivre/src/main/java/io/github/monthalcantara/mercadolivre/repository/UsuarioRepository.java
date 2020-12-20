package io.github.monthalcantara.mercadolivre.repository;

import io.github.monthalcantara.mercadolivre.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByLogin(String login);

    @Query("Select u from Usuario u where u.login like %:login%")
    Page<Usuario> buscaPorLoginQueContem(@Param("login") String login, Pageable pageable);

    boolean existsUsuarioByLogin(String login);
}
