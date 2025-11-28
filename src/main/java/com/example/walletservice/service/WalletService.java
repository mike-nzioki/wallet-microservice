package com.example.walletservice.service;

import com.example.walletservice.domain.Wallet;
import com.example.walletservice.exception.WalletAlreadyExistsException;
import com.example.walletservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
@Service
@RequiredArgsConstructor

public class WalletService {

    private final WalletRepository walletRepository;
    @Transactional
    public Wallet createWallet(UUID userId) {
        if (walletRepository.findByUserId(userId).isPresent()) {
            throw new WalletAlreadyExistsException("User already has a wallet");
        }
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }
    @Transactional
    public BigDecimal deposit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Amount must be positive");
        }
        Wallet wallet = getWalletByUser(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    @Transactional
    public BigDecimal withdraw(UUID userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        Wallet wallet = getWalletByUser(userId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    @Transactional
    public void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet toWallet = walletRepository.findByUserId(toUserId)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    public Wallet getWalletByUser(UUID userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
}
