package com.putoet.day17;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProbeLauncherTest {
    final ProbeLauncher launcher = new ProbeLauncher(Point.of(20, -5), Point.of(30, -10));

    @Test
    void hits() {
        final var hits = launcher.hits();
        assertEquals(112, hits.size());
    }

    @Test
    void highest() {
        final var hits = launcher.hits();
        final var highest = launcher.highest(hits).orElseThrow();
        assertEquals(9, highest.y());
        assertEquals(45, launcher.maxHeight(highest));
    }
}