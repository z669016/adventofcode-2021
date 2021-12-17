package com.putoet.day17;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProbeLauncherTest {
    final ProbeLauncher launcher = new ProbeLauncher(Point.of(20, -5), Point.of(30, -10));

    @Test
    void hits() {
        final List<Point> hits = launcher.hits();
        assertEquals(112, hits.size());
    }

    @Test
    void highest() {
        final List<Point> hits = launcher.hits();
        final Optional<Point> highest = launcher.highest(hits);
        assertTrue(highest.isPresent());
        assertEquals(9, highest.get().y());
        assertEquals(45, launcher.maxHeight(highest.get()));
    }
}