package com.putoet.day8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    private Day8 day8;

    @BeforeEach
    void setup() {
        day8 = new Day8(new String[0]);
    }

    @Test
    void countUniqueNumbers() {
        assertEquals(26, day8.countUniqueNumbers());
    }

    @Test
    void decodeList() {
        int[] expected = new int[]{8394, 9781, 1197, 9361, 4873, 8418, 4548, 1625, 8717, 4315};
        for (int i = 0; i < expected.length; i++) {
            final SignalPattern signalPattern = day8.signalPatterns().get(i);
            assertEquals(expected[i], day8.decode(signalPattern).getAsInt());
        }
    }

    @Test
    void decode() {
        assertEquals(61229, day8.decode());
    }
}