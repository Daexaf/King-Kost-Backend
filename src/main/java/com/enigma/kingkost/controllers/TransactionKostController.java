package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.entities.TransactionKost;
import com.enigma.kingkost.services.TransactionKostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.VALUE_TRANSACTION_KOST)
@RequiredArgsConstructor
public class TransactionKostController {
    private final TransactionKostService transactionKostService;

    @PostMapping
    public ResponseEntity<CommondResponse> createTransaction(@RequestBody TransactionKostRequest transactionKostRequest) {
        TransactionKost transactionKost = transactionKostService.create(transactionKostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success transaction")
                .data(transactionKost)
                .build());
    }

    @GetMapping(value = AppPath.CUSTOMER + AppPath.VALUE_ID)
    public ResponseEntity<CommondResponse> getTransactionByCustomerId(@PathVariable String id) {
        List<TransactionKost> transactionKosts = transactionKostService.getByCustomerId(id);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get transaction")
                .data(transactionKosts)
                .build());
    }

    @GetMapping(AppPath.SELLER + AppPath.VALUE_ID)
    public ResponseEntity<CommondResponse> getBySellerId(@PathVariable String id) {
        List<TransactionKost> transactionKostList = transactionKostService.getBySellerId(id);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get transaction")
                .data(transactionKostList)
                .build());
    }

    @GetMapping(AppPath.VALUE_ID)
    public ResponseEntity<CommondResponse> getTransasctionById(@PathVariable String id) {
        TransactionKost transactionKost = transactionKostService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get transaction")
                .data(transactionKost)
                .build());
    }
}
