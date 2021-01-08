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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ExistsValueValidatorTest {

    @Mock
    private EntityManager manager;

    @Autowired
    private ExistsValue constraintAnnotation;

    @InjectMocks
    private ExistsValueValidator existsValueValidator;


    @Test
    void isValid() {
        existsValueValidator.initialize(constraintAnnotation);
        Categoria categoria = new Categoria("Telefonia");

        Mockito.when(manager.createQuery(Mockito.anyString()).setParameter(Mockito.anyString(), Mockito.any()).getResultList().isEmpty()).thenReturn(true);
        boolean valid = existsValueValidator.isValid(categoria.getId(), null);

        Assertions.assertTrue(valid);

    }
}