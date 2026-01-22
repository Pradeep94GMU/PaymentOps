package com.pradeep.paymentops.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payment_audit_events", indexes = {
        @Index(name = "ix_audit_payment_id_created", columnList = "payment_id, created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", nullable = false, length = 36)
    private String paymentId;

    @Column(name = "from_status", length = 20)
    private String fromStatus;

    @Column(name = "to_status", nullable = false, length = 20)
    private String toStatus;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false, length = 60)
    private String actor;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}

