package com.putoet.day5;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VentTest {

    @Test
    void of() {
        final var vent = Vent.of("0,9 -> 5,9");
        assertEquals(Point.of(0, 9), vent.start());
        assertEquals(Point.of(5, 9), vent.end());
    }
}