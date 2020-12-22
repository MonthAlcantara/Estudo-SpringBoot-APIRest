package io.github.monthalcantara.mercadolivre.repository;

import io.github.monthalcantara.mercadolivre.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
