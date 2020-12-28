package io.github.monthalcantara.mercadolivre.validators;

import io.github.monthalcantara.mercadolivre.model.Categoria;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ExtendWith(SpringExtension.class)
class ExistsValueValidatorTest {



    @Autowired
    private EntityManager manager;

    private ExistsValueValidator existsValueValidator = new ExistsValueValidator(manager);


    @Test
    void isValid() {
        Categoria categoria = new Categoria("Telefonia");
        manager.persist(categoria);
        boolean valid = existsValueValidator.isValid(categoria.getId(), null);

        Assertions.assertTrue(valid);

    }
}