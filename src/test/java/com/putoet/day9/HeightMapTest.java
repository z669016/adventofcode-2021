package com.putoet.day9;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeightMapTest {
    private HeightMap heightMap;

    @BeforeEach
    void setup() {
        heightMap = HeightMap.of(ResourceLines.list("/day9.txt"));
    }

    @Test
    void lowest() {
        final var lowest = heightMap.lowest();
        assertEquals(4, lowest.size());
    }

    @Test
    void riskLevel() {
        final var lowest = heightMap.lowest();
        assertEquals(15, heightMap.riskLevel(lowest));
    }

    @Test
    void basin() {
        final var lowest = heightMap.lowest();

        assertEquals(3, heightMap.basin(lowest.get(0)).size());
        assertEquals(9, heightMap.basin(lowest.get(1)).size());
        assertEquals(14, heightMap.basin(lowest.get(2)).size());
        assertEquals(9, heightMap.basin(lowest.get(3)).size());
    }

    @Test
    void largestBasins() {
        final var largest = heightMap.largestBasins();

        assertEquals(3, largest.size());
        assertEquals(1134, largest.get(0).size() * largest.get(1).size() * largest.get(2).size());
    }
}