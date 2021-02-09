package io.github.monthalcantara.creditas.dto.response;

import io.github.monthalcantara.creditas.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomerResponse {

    private String customer;

    private List<Loan> loans;
}
