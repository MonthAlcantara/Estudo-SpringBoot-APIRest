package io.github.monthalcantara.mercadolivre.repository;

import io.github.monthalcantara.mercadolivre.model.CaracteristicaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaProdutoRepository extends JpaRepository<CaracteristicaProduto, Integer> {
}
