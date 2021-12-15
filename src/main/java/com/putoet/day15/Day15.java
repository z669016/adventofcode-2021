package com.putoet.day15;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.Optional;

public class Day15 extends Day {
    private final Cave cave;

    protected Day15(String[] args) {
        super(args);

        cave = Cave.of(ResourceLines.list("/day15.txt"));
    }

    public static void main(String[] args) {
        final Day day = new Day15(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Optional<CaveSearch.CaveNode> finish =
                CaveSearch.bfs(cave.start(), CaveSearch.goalTest(cave), CaveSearch.successors(cave));
        if (finish.isPresent())
            System.out.println("the lowest total risk of any path from the top left to the bottom right is " + finish.get().totalRisk());
        else
            System.out.println("No route found");
    }

    @Override
    public void part2() {
        final Cave expanded = cave.expand(5);
        final Optional<CaveSearch.CaveNode> finish =
                CaveSearch.bfs(expanded.start(), CaveSearch.goalTest(expanded), CaveSearch.successors(expanded));
        if (finish.isPresent())
            System.out.println("the lowest total risk of any path from the top left to the bottom right in the expanded cave is " + finish.get().totalRisk());
        else
            System.out.println("No route found for the expanded cave");
    }

}
