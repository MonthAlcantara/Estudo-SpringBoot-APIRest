package io.github.monthalcantara.restspringbootestudo.controller;

import io.github.monthalcantara.restspringbootestudo.data.vo.v1.PersonVO;
import io.github.monthalcantara.restspringbootestudo.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}",
            produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO findById(@PathVariable("id") Long id) {
        PersonVO personVO = personService.findById(id);
        // LinkTo e methodOn tiveram de ser importados na mão e de forma static pois não foi encontrado pelo Spring
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        /*
         * Ao personVO (Objeto que será enviado ao client) eu adiciono uma lista de links
         * Pega o PersonController, adiciona um auto relacionamento (um link para ele mesmo) no método findById
         * */
        return personVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public List<PersonVO> findAll() throws Exception {
        List<PersonVO> peoples = personService.findAll();
        // pega todas as pessoas da lista e percorrendo vai adicionando link a cada uma
        peoples
                .stream()
                .forEach(p -> {
                            p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
                        }
                );
        return peoples;
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO create(@RequestBody PersonVO person) {
        PersonVO personVO = personService.create(person);
        // Aqui utilizei o personVO.getKey() por que o person que chega ainda não possui id pois não foi persistido
        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        // personVO.add(linkTo(methodOn(PersonController.class).delete(personVO.getKey())).withSelfRel());
        return personVO;

    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO update(@RequestBody PersonVO person) {
        PersonVO personVO = personService.update(person);
        personVO.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
        return personVO;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        personService.delete(id);
    }


}
