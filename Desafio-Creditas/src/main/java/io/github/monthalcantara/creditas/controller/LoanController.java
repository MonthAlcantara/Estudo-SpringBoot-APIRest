package io.github.monthalcantara.creditas.controller;

import io.github.monthalcantara.creditas.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/loans")
public class LoanController {

    @PostMapping
    public ResponseEntity analyzesProposal(Customer customer){
        return ResponseEntity.ok(null);
    }
}
