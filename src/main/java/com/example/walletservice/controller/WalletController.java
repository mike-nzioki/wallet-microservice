package com.example.walletservice.controller;

import com.example.walletservice.domain.Wallet;
import com.example.walletservice.dto.ApiResponse;
import com.example.walletservice.dto.TransferRequest;
import com.example.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<ApiResponse<Wallet>> createWallet(@RequestParam UUID userId) {
        try {
            Wallet wallet = walletService.createWallet(userId);
            return ResponseEntity.ok(ApiResponse.ok("Wallet created successfully", wallet));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(1001, ex.getMessage()));
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getBalance(@RequestParam UUID userId) {
        try {
            BigDecimal balance = walletService.getWalletByUser(userId).getBalance();
            return ResponseEntity.ok(ApiResponse.ok("Balance fetched successfully", balance));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(1005, ex.getMessage()));
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<BigDecimal>> deposit(
            @RequestParam UUID userId,
            @RequestParam BigDecimal amount
    ) {
        try {
            BigDecimal newBalance = walletService.deposit(userId, amount);
            return ResponseEntity.ok(ApiResponse.ok("Deposit successful", newBalance));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(1002, ex.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<BigDecimal>> withdraw(
            @RequestParam UUID userId,
            @RequestParam BigDecimal amount
    ) {
        try {
            BigDecimal newBalance = walletService.withdraw(userId, amount);
            return ResponseEntity.ok(ApiResponse.ok("Withdrawal successful", newBalance));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(1003, ex.getMessage()));
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<String>> transfer(@RequestBody TransferRequest request) {
        try {
            walletService.transfer(
                    request.getFromUserId(),
                    request.getToUserId(),
                    request.getAmount()
            );
            return ResponseEntity.ok(ApiResponse.ok("Transfer successful", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(1004, ex.getMessage()));
        }
    }

}
