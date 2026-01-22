package com.pradeep.paymentops.repository;

import com.pradeep.paymentops.entity.PaymentAuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentAuditEventRepository extends JpaRepository<PaymentAuditEvent, Long> {
    List<PaymentAuditEvent> findByPaymentIdOrderByCreatedAtAsc(String paymentId);
}
