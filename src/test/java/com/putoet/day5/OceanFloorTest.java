package com.putoet.day5;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OceanFloorTest {
    private static final List<Vent> vents = Vent.of(ResourceLines.list("/day5.txt"));

    @Test
    void overlapSingleLine() {
        final OceanFloor oceanFloor = OceanFloor.of(vents,
                line -> line.start().x() == line.end().x() || line.start().y() == line.end().y());
        assertEquals(5, oceanFloor.overlap());
    }

    @Test
    void overlapDiagonalLine() {
        final OceanFloor oceanFloor = OceanFloor.of(vents, line -> true);
        assertEquals(12, oceanFloor.overlap());
    }
}