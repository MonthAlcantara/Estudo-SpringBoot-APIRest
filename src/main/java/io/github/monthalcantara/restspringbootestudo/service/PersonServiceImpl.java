package io.github.monthalcantara.restspringbootestudo.service;

import io.github.monthalcantara.restspringbootestudo.exception.ResourceNotFoundException;
import io.github.monthalcantara.restspringbootestudo.model.Person;
import io.github.monthalcantara.restspringbootestudo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    private final AtomicLong counter = new AtomicLong();
    private Person person;

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Não foram encontradas pessoas com o id: " + id)
                );
    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        Person personUpdate = findById(person.getId());
        personUpdate.setFirstName(person.getFirstName());
        personUpdate.setLastName(person.getLastName());
        personUpdate.setAddress(person.getAddress());
        personUpdate.setGender(person.getGender());
        return personRepository.save(personUpdate);
    }

    @Override
    public List<Person> findAll() throws Exception {
        return personRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Person person = findById(id);
        personRepository.delete(person);
    }
}

