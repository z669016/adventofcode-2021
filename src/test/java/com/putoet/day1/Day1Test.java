package com.putoet.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
    private static final List<Integer> MEASUREMENTS = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

    @Test
    void countIncreases() {
        assertEquals(7, Day1.countIncreases(MEASUREMENTS));
    }

    @Test
    void groupsOfThree() {
        assertEquals(5, Day1.countIncreases(Day1.groupsOfThree(MEASUREMENTS)));
    }
}