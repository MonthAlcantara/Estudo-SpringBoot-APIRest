package io.github.monthalcantara.mercadolivre.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ExistsValueValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsValue {

    String message() default "não existe nos nossos registros";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> classe();
    String atributo();
}
