package com.putoet.day12;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day12 extends Day {
    private final List<String> input;

    protected Day12(String[] args) {
        super(args);
        input = ResourceLines.list("/day12.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day12(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Maze maze = Maze.of(input);
        final List<MazeSeach.CaveNode> paths =
                MazeSeach.paths(maze.start(), MazeSeach.goalTest(maze), MazeSeach::successors);

        System.out.println("The number of paths through this cave system that visit small caves at most once is " + paths.size());
    }

    @Override
    public void part2() {
        final Maze maze = Maze.of(input);
        final List<MazeSeach.CaveNode> paths =
                MazeSeach.paths(maze.start(), MazeSeach.goalTest(maze), MazeSeach::successorsOneSmall);

        System.out.println("The number of paths through this cave system that visits one small caves at most twice is " + paths.size());
    }
}
