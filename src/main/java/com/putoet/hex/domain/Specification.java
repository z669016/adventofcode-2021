package com.putoet.hex.domain;

import java.util.function.Predicate;

public interface Specification<T> extends Predicate<T> {
    default boolean isSatisfied(T t) {
        return test(t);
    }
    default boolean enforce(T t) {
        if (!test(t)) {
            throw specificationViolation(t);
        }

        return true;
    }

    SpecificationViolation specificationViolation(T t);
}
