package com.pradeep.paymentops.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/health")
    public String health() {
        return "PaymentOps service is running in background";
    }
}
