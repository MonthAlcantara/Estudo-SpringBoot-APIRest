package io.github.monthalcantara.creditas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Loan {

    private String type;

    private int taxes;
}
