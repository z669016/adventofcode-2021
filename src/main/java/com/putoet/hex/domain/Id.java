package com.putoet.hex.domain;

import java.util.Objects;
import java.util.UUID;

public record Id(UUID uuid) {
    public Id {
        Objects.requireNonNull(uuid);
    }

    public static Id generatedId() {
        return new Id(UUID.randomUUID());
    }

    public static Id withId(String uuid) {
        return new Id(UUID.fromString(uuid));
    }
}
