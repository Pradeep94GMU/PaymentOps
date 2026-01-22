package com.pradeep.paymentops.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
    private String paymentId;
    private String merchantId;
    private long amountCents;
    private String currency;
    private String status;
}
