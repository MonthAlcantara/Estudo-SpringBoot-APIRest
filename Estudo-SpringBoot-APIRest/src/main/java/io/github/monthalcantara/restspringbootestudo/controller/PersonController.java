package io.github.monthalcantara.restspringbootestudo.controller;

import io.github.monthalcantara.restspringbootestudo.model.Person;
import io.github.monthalcantara.restspringbootestudo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") String id){
        return personService.findById(id);
    }
    @GetMapping
    public List<Person> findAll(){
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Person person = mockPerson(String.valueOf(i));
            persons.add(person);
        }
    return persons;
    }
    @PostMapping
    public Person save(@RequestBody Person person){
        return personService.create(person);
    }

    private Person mockPerson(String i){
       return personService.findById(i);
    }


}
