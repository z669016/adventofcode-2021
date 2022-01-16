package com.putoet.hex.domain;

public class PolicyViolation extends IllegalArgumentException {
    public PolicyViolation(String message) {
        super(message);
    }
    public PolicyViolation(String message, RuntimeException cause) {
        super(message, cause);
    }
}
