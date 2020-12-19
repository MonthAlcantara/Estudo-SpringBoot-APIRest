package io.github.monthalcantara.mercadolivre.repository;

import io.github.monthalcantara.mercadolivre.controller.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
