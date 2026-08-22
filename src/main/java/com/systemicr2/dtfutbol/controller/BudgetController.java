package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.dto.BudgetRequestDTO;
import com.systemicr2.dtfutbol.dto.BudgetResponseDTO;
import com.systemicr2.dtfutbol.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    // Dependencia: La CPU que procesa la lógica financiera
    private final BudgetService budgetService;

    // Inyección de dependencias por constructor
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    //Endpoint de entrada (POST) para crear un presupuesto
    @PostMapping
    public ResponseEntity<BudgetResponseDTO> createBudgets(@RequestBody BudgetRequestDTO requestDTO) {
        // 1. Delegar el procesamiento al Service
        BudgetResponseDTO savedBudget = budgetService.createBudget(requestDTO);

        // 2. Devolver código de estado  HTTP 201 (created) y el paquete de respuesta
        return new ResponseEntity<>(savedBudget, HttpStatus.CREATED);
    }

    // Endpoint de Salida (GET) para leer todos los presupuestos
    @GetMapping
    public ResponseEntity<List<BudgetResponseDTO>> getAllBudgets() {
        List<BudgetResponseDTO> budgets = budgetService.getAllBudgets();
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }
}

