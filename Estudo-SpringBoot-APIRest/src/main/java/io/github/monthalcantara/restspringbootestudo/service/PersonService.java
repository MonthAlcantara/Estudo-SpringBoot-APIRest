package io.github.monthalcantara.restspringbootestudo.service;

import io.github.monthalcantara.restspringbootestudo.model.Person;

public interface PersonService {
    public Person findById(String id);
    public Person create(Person person);

}
