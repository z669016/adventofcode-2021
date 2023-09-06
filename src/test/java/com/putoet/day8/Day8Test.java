package com.putoet.day8;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    private static final List<SignalPattern> signalPatterns =
            ResourceLines.list("/day8.txt").stream()
                .map(SignalPattern::of)
                .toList();

    @Test
    void countUniqueSignalPatterns() {
        assertEquals(26, Day8.countUniqueSignalPatterns(signalPatterns));
    }

    @Test
    void decodeList() {
        final var expected = new int[]{8394, 9781, 1197, 9361, 4873, 8418, 4548, 1625, 8717, 4315};
        for (var i = 0; i < expected.length; i++) {
            final var signalPattern = signalPatterns.get(i);
            assertEquals(expected[i], Day8.decode(signalPattern).orElseThrow());
        }
    }

    @Test
    void decode() {
        assertEquals(61229, Day8.decode(signalPatterns));
    }
}