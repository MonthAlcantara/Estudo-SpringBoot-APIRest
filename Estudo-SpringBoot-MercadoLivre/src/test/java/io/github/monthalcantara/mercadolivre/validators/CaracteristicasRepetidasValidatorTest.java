package io.github.monthalcantara.mercadolivre.validators;

import io.github.monthalcantara.mercadolivre.dto.request.NovoProdutoRequest;
import io.github.monthalcantara.mercadolivre.util.CaracteristicasTestFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static io.github.monthalcantara.mercadolivre.util.CaracteristicasTestFactory.geraListaCaracteristicas;
import static io.github.monthalcantara.mercadolivre.util.CaracteristicasTestFactory.geraListaCaracteristicasRepetidas;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CaracteristicasRepetidasValidatorTest {

    @InjectMocks
    private CaracteristicasRepetidasValidator validator;

    private Errors errors;

    @Test
    @Order(1)
    @DisplayName("Deveria rejeitar caracteristica repetida")
    void validateTest() {

        NovoProdutoRequest target = CaracteristicasTestFactory.criaProdutoRequest(geraListaCaracteristicasRepetidas());

        errors = new BeanPropertyBindingResult(target, "teste");
        validator.validate(target, errors);

        Assertions.assertTrue(errors.hasErrors());
    }

    @Test
    @Order(2)
    @DisplayName("Deveria liberar criação de caracteristicas não repetidas")
    void validateTest2() {

        NovoProdutoRequest target = CaracteristicasTestFactory.criaProdutoRequest(geraListaCaracteristicas(3));

        errors = new BeanPropertyBindingResult(target, "teste2");
        validator.validate(target, errors);

        Assertions.assertFalse(errors.hasErrors());
    }


}