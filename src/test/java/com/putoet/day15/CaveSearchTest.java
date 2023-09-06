package com.putoet.day15;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CaveSearchTest {
    private final Cave cave = Cave.of(ResourceLines.list("/day15.txt"));

    @Test
    void bfs() {
        final var finish = CaveSearch.bfs(cave.start(), CaveSearch.goalTest(cave), CaveSearch.successors(cave));

        assertTrue(finish.isPresent());
        assertEquals(40, finish.get().totalRisk());
    }
}