package io.github.monthalcantara.restspringbootestudo.controller;

import io.github.monthalcantara.restspringbootestudo.model.Person;
import io.github.monthalcantara.restspringbootestudo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @GetMapping
    public List<Person> findAll() throws Exception {
        return personService.findAll();
    }

    @PostMapping
    public Person save(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        personService.delete(id);
    }



}
