package com.putoet.day5;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OceanFloorTest {

    @Test
    void overlapSingleLine() {
        final OceanFloor oceanFloor = OceanFloor.of(ResourceLines.list("/day5.txt"),
                line -> line.start().x == line.end().x || line.start().y == line.end().y);
        System.out.println(oceanFloor);
        assertEquals(5, oceanFloor.overlap());
    }

    @Test
    void overlapDiagonalLine() {
        final OceanFloor oceanFloor = OceanFloor.of(ResourceLines.list("/day5.txt"), line -> true);
        System.out.println(oceanFloor);
        assertEquals(12, oceanFloor.overlap());
    }
}