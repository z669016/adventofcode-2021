package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Specification;
import com.putoet.hex.domain.SpecificationViolation;

public record SeaCucumberMoveToSpec(Point to) implements Specification<SeaCucumber> {
    public static final String VIOLATION = "A sea cucumber can only move 1 position in the direction " +
            "matching its type. Sea cucumber %s if of type %s cannot move from %s to %s";

    @Override
    public boolean test(SeaCucumber seaCucumber) {
        final boolean valid = switch (seaCucumber.type()) {
            case RIGHT -> to.y() == seaCucumber.location().y();
            case DOWN -> to.x() == seaCucumber.location().x();
        };

        return valid && to.manhattanDistance(seaCucumber.location()) == 1;
    }

    @Override
    public SpecificationViolation specificationViolation(SeaCucumber seaCucumber) {
        return new SpecificationViolation(String.format(VIOLATION, seaCucumber.id(), seaCucumber.type(),
                seaCucumber.location(), to));
    }
}
