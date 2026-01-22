package com.pradeep.paymentops.repository;


import com.pradeep.paymentops.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}

