package com.putoet.day1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    @Test
    void countIncreases() {
        final List<Integer> measurements = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);
        assertEquals(7, Day1.countIncreases(measurements));
    }
}