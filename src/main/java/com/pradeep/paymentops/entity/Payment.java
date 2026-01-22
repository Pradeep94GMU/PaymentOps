package com.pradeep.paymentops.entity;
import jakarta.persistence.Version;
import java.time.OffsetDateTime;


import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "ix_payments_merchant", columnList = "merchant_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "merchant_id", nullable = false, length = 60)
    private String merchantId;

    @Column(name = "amount_cents", nullable = false)
    private long amountCents;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;


    @Version
    private long version;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;



}

