package com.putoet.day12;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MazeSearchTest {
    public static final int[] SINGLE_SIZES = new int[]{10, 19, 226};
    public static final int[] DOUBLE_SIZES = new int[]{36, 103, 3509};
    private static final List<List<String>> MAZES = List.of(
            List.of(
                    "start-A",
                    "start-b",
                    "A-c",
                    "A-b",
                    "b-d",
                    "A-end",
                    "b-end"
            ),
            List.of(
                    "dc-end",
                    "HN-start",
                    "start-kj",
                    "dc-start",
                    "dc-HN",
                    "LN-dc",
                    "HN-end",
                    "kj-sa",
                    "kj-HN",
                    "kj-dc"
            ),
            List.of(
                    "fs-end",
                    "he-DX",
                    "fs-he",
                    "start-DX",
                    "pj-DX",
                    "end-zg",
                    "zg-sl",
                    "zg-pj",
                    "pj-he",
                    "RW-he",
                    "fs-DX",
                    "pj-RW",
                    "zg-RW",
                    "start-pj",
                    "he-WI",
                    "zg-he",
                    "pj-fs",
                    "start-RW"
            )
    );

    @Test
    void paths() {
        for (var idx = 0; idx < MAZES.size(); idx++) {
            testPaths(MAZES.get(idx), SINGLE_SIZES[idx], MazeSearch::successors);
        }
    }

    void testPaths(List<String> input, int expectedSize, Function<MazeSearch.CaveNode, List<Cave>> successors) {
        final var maze = Maze.of(input);
        final var paths = MazeSearch.paths(maze.start(), MazeSearch.goalTest(maze), successors);
        assertEquals(expectedSize, paths.size());
    }

    @Test
    void doublePaths() {
        for (var idx = 0; idx < MAZES.size(); idx++) {
            testPaths(MAZES.get(idx), DOUBLE_SIZES[idx], MazeSearch::successorsOneSmall);
        }
    }
}