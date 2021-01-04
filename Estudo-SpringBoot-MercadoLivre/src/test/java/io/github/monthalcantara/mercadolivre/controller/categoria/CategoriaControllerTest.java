package io.github.monthalcantara.mercadolivre.controller.categoria;

import io.github.monthalcantara.mercadolivre.model.Categoria;
import io.github.monthalcantara.mercadolivre.repository.CategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CategoriaControllerTest {

    @Mock
    private CategoriaRepository repository;
    @InjectMocks
    private CategoriaController categoriaController;

    @Test
    void criaNovaCategoria() {

    }

    @Test
    void buscaCategoriaPeloId() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(geraCategoria()));
        ResponseEntity responseEntity = categoriaController.buscaCategoriaPeloId(UUID.randomUUID());

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    public Categoria geraCategoria(){
        return new Categoria("Tecnologia");
    }

}