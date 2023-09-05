package com.putoet.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Day1Test {
    private static final List<Integer> MEASUREMENTS = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

    @Test
    void countIncreases() {
        assertThrows(AssertionError.class, () -> Day1.countIncreases(List.of()));

        assertEquals(7, Day1.countIncreases(MEASUREMENTS));
    }

    @Test
    void groupsOfThree() {
        assertThrows(AssertionError.class, () -> Day1.groupsOfThree(List.of(1)));
        assertThrows(AssertionError.class, () -> Day1.groupsOfThree(List.of(1, 2)));

        assertEquals(List.of(607, 618, 618, 617, 647, 716, 769, 792), Day1.groupsOfThree(MEASUREMENTS));
    }
}