package io.github.monthalcantara.mercadolivre.util;

import io.github.monthalcantara.mercadolivre.dto.request.CaracteristicaProdutoRequest;
import io.github.monthalcantara.mercadolivre.dto.request.NovoProdutoRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CaracteristicasTestFactory {


    public static NovoProdutoRequest criaProdutoRequest(List<CaracteristicaProdutoRequest> listaCaracteristicas) {
        return new NovoProdutoRequest("NomeTest",
                BigDecimal.valueOf(10),
                1,
                listaCaracteristicas,
                "Produto",
                UUID.randomUUID());
    }

    public static List<CaracteristicaProdutoRequest> geraListaCaracteristicasRepetidas() {
        CaracteristicaProdutoRequest caracteristica = criaCaracteristica();
        return Arrays.asList(caracteristica, caracteristica, caracteristica);
    }

    public static List<CaracteristicaProdutoRequest> geraListaCaracteristicas(int qtdElementos) {
        List<CaracteristicaProdutoRequest> caracteristicas = new ArrayList<CaracteristicaProdutoRequest>();
        for (int i = 0; i < qtdElementos; i++) {
            caracteristicas.add(new CaracteristicaProdutoRequest("caracteristicaTest" + i, "test"));
        }
        return caracteristicas;
    }

    public static CaracteristicaProdutoRequest criaCaracteristica() {
        return new CaracteristicaProdutoRequest("caracteristicaTest", "test");
    }
}
