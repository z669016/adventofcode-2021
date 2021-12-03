package com.putoet.day3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiagnosticsTest {
    private static final List<String> DIAGNOSTICS = List.of(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
    );

    @Test
    void gammaRate() {
        assertEquals(22, Diagnostics.gammaRate(DIAGNOSTICS));
    }

    @Test
    void epsilonRate() {
        assertEquals(9, Diagnostics.epsilonRate(DIAGNOSTICS));
    }

    @Test
    void oxygenGeneratorRate() {
        assertEquals(23, Diagnostics.oxygenGeneratorRate(DIAGNOSTICS));
    }

    @Test
    void co2ScrubberRating() {
        assertEquals(10, Diagnostics.co2ScrubberRating(DIAGNOSTICS));
    }
}