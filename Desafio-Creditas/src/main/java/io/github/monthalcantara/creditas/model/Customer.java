package io.github.monthalcantara.creditas.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Customer {

    private String name;

    private String cpf;

    private int age;

    private String location;

    private int income;
}
