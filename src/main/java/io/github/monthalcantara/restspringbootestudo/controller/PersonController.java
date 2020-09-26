package io.github.monthalcantara.restspringbootestudo.controller;

import io.github.monthalcantara.restspringbootestudo.data.vo.v1.PersonVO;
import io.github.monthalcantara.restspringbootestudo.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public PersonVO findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @GetMapping
    public List<PersonVO> findAll() throws Exception {
        return personService.findAll();
    }

    @PostMapping
    public PersonVO save(@RequestBody PersonVO person) {
        return personService.create(person);
    }

    @PutMapping
    public PersonVO update(@RequestBody PersonVO person) {
        return personService.update(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        personService.delete(id);
    }



}
