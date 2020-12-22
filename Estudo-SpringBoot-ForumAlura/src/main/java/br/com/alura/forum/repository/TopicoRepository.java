package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
	@Query("Select t from Topico t where t.curso.nome =:nomeCurso")
	Page<Topico> buscaPeloNomeDoCurso(@Param("nomeCurso") String nomeCurso, Pageable pageable);

}
