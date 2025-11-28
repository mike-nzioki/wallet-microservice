package com.example.walletservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class TransferRequest {
    private UUID fromUserId;
    private UUID toUserId;
    private BigDecimal amount;

}
