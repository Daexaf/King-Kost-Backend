package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.GetAllTransactionRequest;
import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.dto.response.CommondResponseNoData;
import com.enigma.kingkost.dto.response.CommondResponseWithPagging;
import com.enigma.kingkost.dto.response.PaggingResponse;
import com.enigma.kingkost.entities.TransactionKost;
import com.enigma.kingkost.services.TransactionKostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequestMapping(AppPath.VALUE_TRANSACTION_KOST)
@RequiredArgsConstructor
public class TransactionKostController {
    private final TransactionKostService transactionKostService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CommondResponseNoData> createTransaction(@RequestBody TransactionKostRequest transactionKostRequest) {
        transactionKostService.create(transactionKostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponseNoData.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success transaction")
                .build());
    }

    @GetMapping
    public ResponseEntity<CommondResponseWithPagging> getAllTransactionWithManyRequest(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "5") Integer size, @RequestParam(name = "customerId", required = false) String customerId, @RequestParam(name = "sellerId", required = false) String sellerId, @RequestParam(name = "approveStatus", required = false) Integer approveStatus) {
        Page<TransactionKost> transactionKosts = transactionKostService.getAllTransaction(GetAllTransactionRequest.builder()
                .page(page)
                .size(size)
                .customerId(customerId)
                .sellerId(sellerId)
                .aprovStatus(approveStatus)
                .build());
        PaggingResponse paggingResponse = PaggingResponse.builder()
                .currentPage(page)
                .size(size)
                .totalPage(transactionKosts.getTotalPages())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(CommondResponseWithPagging.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get all transaction")
                .data(transactionKosts.getContent())
                .paggingResponse(paggingResponse)
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

//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(AppPath.VALUE_CANCEL)
    public ResponseEntity<CommondResponseNoData> cancelTransaction(@RequestParam("transactionId") String transactionId, @RequestParam("customerId") String customerId) {
        transactionKostService.cancelTransactionKost(customerId, transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponseNoData.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success cancel transaction")
                .build());
    }
}
