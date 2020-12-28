package io.github.monthalcantara.mercadolivre.validators;

import io.github.monthalcantara.mercadolivre.dto.request.CaracteristicaProdutoRequest;
import io.github.monthalcantara.mercadolivre.dto.request.NovoProdutoRequest;
import io.github.monthalcantara.mercadolivre.model.Categoria;
import io.github.monthalcantara.mercadolivre.util.CaracteristicasTestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("Teste do Validador Personalizado criado a partir do Validator do Spring")
class CaracteristicasRepetidasValidatorTest {


    /*
     * Dei um new no Validador que eu criei. Como ele não recebe nada por parametro
     * foi mais tranquilo. Não usei o @Autowired para não ter que usar o contexto do
     * Spring. Dessa forma nem precisei anotar a minha classe
     * */
    private CaracteristicasRepetidasValidator validator = new CaracteristicasRepetidasValidator();

    /*
    * Essa interface do spring é uma dependencia da classe de validação que estou testando
    * Posso mockar, injetar ou instanciar (nesse caso que existe uma implementação)
    * mockando eu fujo do natural, injetando eu preciso subir o contexto do spring e
    * instanciando eu preciso de uma implementação dessa Interface
    * */
    private Errors errors;

    @ParameterizedTest
    @MethodSource("geradorTest1")
    @DisplayName("Deveria liberar criação de caracteristicas não repetidas")
    void validateTest2(List<CaracteristicaProdutoRequest> listaCaracteristicasRequests) throws Exception {

        NovoProdutoRequest target = CaracteristicasTestFactory.criaProdutoRequest(listaCaracteristicasRequests);

        /*
        * Poderia ter mockado o Errors e verificado com o Mockito.verify se o método rejectErrors foi chamado
        * mas como a ideia é fazer o teste o mais próximo possível do real, Usei uma implementação do proprio
        * Spring do Errors onde no construtor eu passo o objeto que será testado e um nome qualquer
        * */
        errors = new BeanPropertyBindingResult(target, "teste2");
        validator.validate(target, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @ParameterizedTest
    @MethodSource("geradorTest")
    @DisplayName("Deveria rejeitar caracteristica com nome repetido")
    void validateTest(List<CaracteristicaProdutoRequest> listaCaracteristicasRequests) throws Exception {
        Categoria categoria = new Categoria("categoria");


        NovoProdutoRequest target = CaracteristicasTestFactory.criaProdutoRequest(listaCaracteristicasRequests);

        errors = new BeanPropertyBindingResult(target, "teste");
        validator.validate(target, errors);

        Assertions.assertTrue(errors.hasErrors());
    }


    static Stream<Arguments> geradorTest1() {
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaProdutoRequest("Test", "test"),
                                new CaracteristicaProdutoRequest("Test1", "test1"),
                                new CaracteristicaProdutoRequest("Test2", "test2"))),
                Arguments.of(
                        List.of(new CaracteristicaProdutoRequest("Test", "test"),
                                new CaracteristicaProdutoRequest("Test1", "test1"),
                                new CaracteristicaProdutoRequest("Test2", "test2"),
                                new CaracteristicaProdutoRequest("Test3", "test3"))));
    }

    static Stream<Arguments> geradorTest() {
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaProdutoRequest("Test", "test"),
                                new CaracteristicaProdutoRequest("Test2", "test"),
                                new CaracteristicaProdutoRequest("Test2", "test2"))));
    }

}