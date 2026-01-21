package com.pradeep.paymentops.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Valid
public class CreatePaymentRequest {

    @NotBlank(message = "merchantId is required")
    private String merchantId;

    @Min(value = 1, message = "amountCents must be >= 1")
    private long amountCents;

    @NotBlank(message = "currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "currency must be ISO like USD")
    private String currency;
}
