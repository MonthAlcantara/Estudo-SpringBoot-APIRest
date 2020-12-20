package io.github.monthalcantara.mercadolivre.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueValueValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

    String message() default "não pode ser repetido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> classe();
    String atributo();
}
