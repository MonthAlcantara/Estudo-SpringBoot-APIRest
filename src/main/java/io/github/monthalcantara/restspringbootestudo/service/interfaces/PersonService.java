package io.github.monthalcantara.restspringbootestudo.service.interfaces;

import io.github.monthalcantara.restspringbootestudo.data.vo.v1.PersonVO;

import java.util.List;

public interface PersonService {
    public PersonVO findById(Long id);
    public PersonVO create(PersonVO person);
    public PersonVO update(PersonVO person);
    public List<PersonVO> findAll() throws Exception;
    public void delete(Long id);

}
