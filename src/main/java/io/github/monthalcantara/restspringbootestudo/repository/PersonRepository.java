package io.github.monthalcantara.restspringbootestudo.repository;

import io.github.monthalcantara.restspringbootestudo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
