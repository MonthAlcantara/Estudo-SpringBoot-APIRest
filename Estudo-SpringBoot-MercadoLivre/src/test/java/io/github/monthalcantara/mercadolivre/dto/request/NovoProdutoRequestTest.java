package io.github.monthalcantara.mercadolivre.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


class NovoProdutoRequestTest {
    /*
      Teste Parametrizado
    */
    @ParameterizedTest
    /*
     * Fonte de dados esperado nos parametros será o o método STATIC geradorTest
     * */
    @MethodSource("geradorTest")
    @DisplayName("Cria produto com diversos tipos de caracteristicas")
    /*
     * O listaCaracteristicasRequests recebido por parâmetro é o retorno do método estático geradorTest
     */
    void temCaracteristicaRepetidaTest(List<CaracteristicaProdutoRequest> listaCaracteristicasRequests) throws Exception {

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new NovoProdutoRequest("Carro", BigDecimal.TEN, 10, listaCaracteristicasRequests, "teste", UUID.randomUUID()));
        Assertions.assertTrue(illegalArgumentException.getMessage().contains("O produto precisa ter no mínimo 3 caracteristicas para ser cadastrado"));
    }

    /*
     * Método informado no @MethodSource. Retorna um Stream de argumentos, ou seja
     * se eu passar 20 argumentos, o método de teste será repetido 20 vezes e cada
     * vez receberá um desses argumentos gerados como parametro. Nesse exemplo foram geradas listas
     * mas não precisa ser só collection, funciona com Objeto também
     * */
    static Stream<Arguments> geradorTest() {
        //Crie um Stream de...
        return Stream.of(
                //Crie um Argumento de...
                Arguments.of(
                        //Crie uma lista de...
                        List.of(new CaracteristicaProdutoRequest("Test", "test"),
                                new CaracteristicaProdutoRequest("Test2", "test2"))),

                Arguments.of(
                        List.of(new CaracteristicaProdutoRequest("Test2", "test2"))),

                Arguments.of(List.of()));
    }
}