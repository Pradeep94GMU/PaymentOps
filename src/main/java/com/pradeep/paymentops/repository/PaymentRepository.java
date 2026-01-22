package com.pradeep.paymentops.repository;


import com.pradeep.paymentops.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Page<Payment> findByMerchantId(String merchantId, Pageable pageable);
    Page<Payment> findByStatus(String status, Pageable pageable);
    Page<Payment> findByMerchantIdAndStatus(String merchantId, String status, Pageable pageable);
}


