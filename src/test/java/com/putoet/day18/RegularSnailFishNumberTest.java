package com.putoet.day18;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RegularSnailFishNumberTest {
    private SnailFishNumber number;

    @BeforeEach
    void setup() {
        number = SnailFishNumbers.of(11);
    }

    @Test
    void parent() {
        assertNull(number.parent());
    }

    @Test
    void setParent() {
        final var compound = mock(RegularSnailFishNumber.class);
        number.setParent(compound);

        assertEquals(compound,number.parent());
    }

    @Test
    void magnitude() {
        assertEquals(11L,number.magnitude());
    }

    @Test
    void add() {
        number.add(SnailFishNumbers.of(7));
        assertEquals(18L,number.magnitude());

        final var compound = mock(CompoundSnailFishNumber.class);
        assertThrows(IllegalArgumentException.class, () -> number.add(compound));
    }

    @Test
    void canSplit() {
        assertTrue(number.canSplit());
    }

    @Test
    void split() {
        final var split = number.split();
        assertEquals(27L, split.magnitude());
    }

    @Test
    void canExplode() {
        assertFalse(number.canExplode());
    }

    @Test
    void explode() {
        assertThrows(NotImplementedException.class, () -> number.explode());
    }

    @Test
    void testToString() {
        assertEquals("11", number.toString());
    }

    @Test
    void moveLeftFrom() {
        final var other = SnailFishNumbers.of(9);
        final var compound = mock(CompoundSnailFishNumber.class);
        number.setParent(compound);
        number.moveLeftFrom(other, compound);
        assertEquals(20L, number.magnitude());

        assertThrows(AssertionError.class, () -> number.moveLeftFrom(compound,compound));
    }

    @Test
    void moveRightFrom() {
        final var other = SnailFishNumbers.of(7);
        final var compound = mock(CompoundSnailFishNumber.class);
        number.setParent(compound);
        number.moveRightFrom(other, compound);
        assertEquals(18L, number.magnitude());

        assertThrows(AssertionError.class, () -> number.moveRightFrom(compound,compound));
    }

    @Test
    void reduce() {
        assertFalse(number.reduce());
    }
}