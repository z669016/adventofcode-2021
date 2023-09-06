package com.putoet.day12;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day12 {

    public static void main(String[] args) {
        final var maze = Maze.of(ResourceLines.list("/day12.txt"));

        Timer.run(() -> part1(maze));
        Timer.run(() -> part2(maze));
    }

    static void part1(Maze maze) {
        final var paths = MazeSearch.paths(maze.start(), MazeSearch.goalTest(maze), MazeSearch::successors);
        System.out.println("The number of paths through this cave system that visit small caves at most once is " + paths.size());
    }

    static void part2(Maze maze) {
        final var paths = MazeSearch.paths(maze.start(), MazeSearch.goalTest(maze), MazeSearch::successorsOneSmall);
        System.out.println("The number of paths through this cave system that visits one small caves at most twice is " + paths.size());
    }
}
