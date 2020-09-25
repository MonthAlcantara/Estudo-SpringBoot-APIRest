package io.github.monthalcantara.restspringbootestudo.service;

import io.github.monthalcantara.restspringbootestudo.model.Person;

import java.util.List;

public interface PersonService {
    public Person findById(Long id);
    public Person create(Person person);
    public Person update(Person person);
    public List<Person> findAll() throws Exception;
    public void delete(Long id);

}
