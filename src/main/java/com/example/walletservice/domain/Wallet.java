package com.example.walletservice.domain;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wallets", uniqueConstraints = {
@UniqueConstraint(columnNames = "userId")
})
@Data
public class Wallet {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
}
