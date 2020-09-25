package io.github.monthalcantara.restspringbootestudo.service;

import io.github.monthalcantara.restspringbootestudo.adapter.DozerConverter;
import io.github.monthalcantara.restspringbootestudo.exception.ResourceNotFoundException;
import io.github.monthalcantara.restspringbootestudo.data.model.Person;
import io.github.monthalcantara.restspringbootestudo.data.vo.PersonVO;
import io.github.monthalcantara.restspringbootestudo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonVO findById(Long id) {
        return personRepository.findById(id).map(person ->{
                return converterPersonToVo(person);
        })
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Não foram encontradas pessoas com o id: " + id)
                );
    }

    @Override
    public PersonVO create(PersonVO person) {
    Person personSave = personRepository.save(converterVoToPerson(person));
        return converterPersonToVo(personSave);
    }

    @Override
    public PersonVO update(PersonVO person) {
        Person personUpdate = converterVoToPerson(findById(person.getId()));
        personUpdate.setFirstName(person.getFirstName());
        personUpdate.setLastName(person.getLastName());
        personUpdate.setAddress(person.getAddress());
        personUpdate.setGender(person.getGender());
        return converterPersonToVo(personRepository.save(personUpdate));
    }

    @Override
    public List<PersonVO> findAll() {
        return converterListToVo(personRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Person person = converterVoToPerson(findById(id));
        personRepository.delete(person);
    }

    private Person converterVoToPerson(PersonVO person){
        return DozerConverter.parseObject(person, Person.class);
    }

    private PersonVO converterPersonToVo(Person person){
        return DozerConverter.parseObject(person, PersonVO.class);
    }

    private List<PersonVO> converterListToVo(List<Person> people){
        return DozerConverter.parseListObjects(people, PersonVO.class);
    }
}

