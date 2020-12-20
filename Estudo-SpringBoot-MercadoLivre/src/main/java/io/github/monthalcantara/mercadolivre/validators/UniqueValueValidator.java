package io.github.monthalcantara.mercadolivre.validators;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String atributo;
    private Class<?> classe;
    private EntityManager manager;

    public UniqueValueValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.atributo = constraintAnnotation.atributo();
        this.classe = constraintAnnotation.classe();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return manager.createQuery("Select x from " + classe.getName() + " x where x." + atributo + " =:value")
                .setParameter("value", value)
                .getResultList()
                .isEmpty();

    }
}
