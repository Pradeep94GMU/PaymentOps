package com.pradeep.paymentops.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldViolation> violations;

    @Getter
    @Builder
    public static class FieldViolation {
        private String field;
        private String message;
    }
}

