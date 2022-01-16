package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Specification;
import com.putoet.hex.domain.SpecificationViolation;

public record SeaCucumberWithinSizeSpec(Point size) implements Specification<SeaCucumber> {
    public static String VIOLATION = "Sea cucumber %s on location %s cannot live on a sea floor of size %s";

    @Override
    public SpecificationViolation specificationViolation(SeaCucumber cucumber) {
        return new SpecificationViolation(String.format(VIOLATION, cucumber.id(), cucumber.location(), size));
    }

    @Override
    public boolean test(SeaCucumber cucumber) {
        return cucumber.location().y() >= 0 && cucumber.location().y() < size.y() &&
                cucumber.location().x() >= 0 && cucumber.location().x() < size.x();
    }
}
