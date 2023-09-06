package com.putoet.day12;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void of() {
        final var maze = Maze.of(ResourceLines.list("/day12.txt"));
        assertEquals(6, maze.size());
    }
}