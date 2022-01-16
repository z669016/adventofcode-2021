package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Id;
import com.putoet.hex.domain.SpecificationViolation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaCucumberTest {
    private final SeaCucumber cucumber = new SeaCucumber(Id.generatedId(), SeaCucumberType.RIGHT, Point.ORIGIN);

    @Test
    void constructor() {
        assertThrows(AssertionError.class, () -> new SeaCucumber(null, SeaCucumberType.RIGHT, Point.ORIGIN));
        assertThrows(AssertionError.class, () -> new SeaCucumber(Id.generatedId(), null, Point.ORIGIN));
        assertThrows(AssertionError.class, () -> new SeaCucumber(Id.generatedId(), SeaCucumberType.RIGHT, null));
    }

    @Test
    void location() {
        assertEquals(Point.ORIGIN, cucumber.location());
    }

    @Test
    void type() {
        assertEquals(SeaCucumberType.RIGHT, cucumber.type());
    }

    @Test
    void symbol() {
        assertEquals(SeaCucumberType.RIGHT.toString(), String.valueOf(cucumber.symbol()));
    }

    @Test
    void id() {
        assertNotNull(cucumber.id());
    }

    @Test
    void compareTo() {
        final SeaCucumber left = new SeaCucumber(Id.generatedId(), SeaCucumberType.RIGHT, Point.of(-1, 0));
        final SeaCucumber right = new SeaCucumber(Id.generatedId(), SeaCucumberType.RIGHT, Point.of(1, 0));
        final SeaCucumber up = new SeaCucumber(Id.generatedId(), SeaCucumberType.DOWN, Point.of(0, 1));
        final SeaCucumber down = new SeaCucumber(Id.generatedId(), SeaCucumberType.DOWN, Point.of(0, -1));
        final SeaCucumber equal = new SeaCucumber(Id.generatedId(), SeaCucumberType.DOWN, Point.ORIGIN);

        assertEquals(0, cucumber.compareTo(equal));
        assertEquals(-1, cucumber.compareTo(up));
        assertEquals(-1, cucumber.compareTo(right));
        assertEquals(1, cucumber.compareTo(down));
        assertEquals(1, cucumber.compareTo(left));
    }

    @Test
    void testEquals() {
        assertEquals(cucumber, cucumber);
        assertNotEquals(cucumber, null);
    }
}