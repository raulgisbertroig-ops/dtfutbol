package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.dto.TransactionRequestDTO;
import com.systemicr2.dtfutbol.dto.TransactionResponseDTO;
import com.systemicr2.dtfutbol.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    // Dependencia: La CPU transaccional
    private final TransactionService transactionService;

    // Inyección por constructor
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Puerto de entrada para registrar nuevos movimientos financieros
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO requestDTO) {
        TransactionResponseDTO savedTransaction = transactionService.createTransaction(requestDTO);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }
}