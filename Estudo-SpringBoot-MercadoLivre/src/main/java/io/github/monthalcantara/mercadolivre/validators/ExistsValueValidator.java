package io.github.monthalcantara.mercadolivre.validators;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValueValidator implements ConstraintValidator<ExistsValue, Object> {

    private Class<?> classe;
    private String attributo;

    private EntityManager manager;

    public ExistsValueValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(ExistsValue constraintAnnotation) {
        this.attributo = constraintAnnotation.atributo();
        this.classe = constraintAnnotation.classe();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }

        return !manager
                .createQuery("Select x from " + classe.getSimpleName() + " x where x." + attributo + " =:value")
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }
}
