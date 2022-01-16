package com.putoet.hex.domain;

public class SpecificationViolation extends IllegalArgumentException {
    public SpecificationViolation(String message) {
        super(message);
    }
}
