package com.pradeep.paymentops.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class PaymentAuditResponse {
    private Long id;
    private String fromStatus;
    private String toStatus;
    private String reason;
    private String actor;
    private OffsetDateTime createdAt;
}
