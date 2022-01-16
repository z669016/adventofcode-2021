package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Id;
import com.putoet.hex.domain.SpecificationViolation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaCucumberMoveToSpecTest {
    private static final SeaCucumber CUCUMBER = new SeaCucumber(Id.generatedId(), SeaCucumberType.RIGHT, Point.ORIGIN);

    @Test
    void testValidSpecRight() {
        final SeaCucumberMoveToSpec validSpec = new SeaCucumberMoveToSpec(Point.WEST);
        assertTrue(validSpec.isSatisfied(CUCUMBER));
    }

    @Test
    void testValidSpecDown() {
        final SeaCucumber cucumber = new SeaCucumber(Id.generatedId(), SeaCucumberType.DOWN, Point.ORIGIN);
        final SeaCucumberMoveToSpec validSpec = new SeaCucumberMoveToSpec(Point.SOUTH);
        assertTrue(validSpec.isSatisfied(cucumber));
    }

    @Test
    void testInvalidSpecRight() {
        final SeaCucumberMoveToSpec invalidSpecDistance = new SeaCucumberMoveToSpec(Point.of(10, 0));
        assertFalse(invalidSpecDistance.isSatisfied(CUCUMBER));
        assertThrows(SpecificationViolation.class, () -> invalidSpecDistance.enforce(CUCUMBER));

        final SeaCucumberMoveToSpec invalidSpecRight = new SeaCucumberMoveToSpec(Point.of(1, 1));
        assertFalse(invalidSpecRight.isSatisfied(CUCUMBER));
        assertThrows(SpecificationViolation.class, () -> invalidSpecRight.enforce(CUCUMBER));
    }

    @Test
    void testInvalidSpecDown() {
        final SeaCucumberMoveToSpec invalidSpecDistance = new SeaCucumberMoveToSpec(Point.of(0, 10));
        assertFalse(invalidSpecDistance.isSatisfied(CUCUMBER));
        assertThrows(SpecificationViolation.class, () -> invalidSpecDistance.enforce(CUCUMBER));

        final SeaCucumberMoveToSpec invalidSpecDown = new SeaCucumberMoveToSpec(Point.of(1, 1));
        assertFalse(invalidSpecDown.isSatisfied(CUCUMBER));
        assertThrows(SpecificationViolation.class, () -> invalidSpecDown.enforce(CUCUMBER));
    }
}