package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.response.CommondResponseNoData;
import com.enigma.kingkost.services.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppPath.VALUE_APPROV)
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;

    @PostMapping
    public ResponseEntity<CommondResponseNoData> approvTransaction(@RequestParam("transactionId") String transactionId, @RequestParam("approv") Integer approv, @RequestParam("sellerId") String sellerId) {
        approvalService.approveTransactionKost(transactionId, approv, sellerId);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponseNoData.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success approv transaction")
                .build());
    }
}
