package io.github.monthalcantara.restspringbootestudo.service;

import io.github.monthalcantara.restspringbootestudo.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServiceImpl implements PersonService{

    private final AtomicLong counter = new AtomicLong();

    public Person findById(String id){
       Person person = new Person();
       person.setId(counter.incrementAndGet());
       person.setFirstName("Month");
       person.setLastName("Junior");
       person.setAndress("Lauro de Freitas - Bahia - Brasil");
       person.setGender("Male");
        return person;
    }

    public Person create(Person person) {
        return person;
    }
}
