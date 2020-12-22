package io.github.monthalcantara.mercadolivre.validators;

import io.github.monthalcantara.mercadolivre.dto.request.NovoProdutoRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class CaracteristicasRepetidasValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return NovoProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object value, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NovoProdutoRequest novoProdutoRequest = (NovoProdutoRequest) value;

        Set<String> caracteristicasRepetidas = novoProdutoRequest.temCaracteristicaRepetida();
        if (!caracteristicasRepetidas.isEmpty()) {

            errors.rejectValue("Caracteristicas", null,"não pode ser repetido " + caracteristicasRepetidas);
        }
    }
}
