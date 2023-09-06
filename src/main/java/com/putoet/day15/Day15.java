package com.putoet.day15;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day15 {
    public static void main(String[] args) {
        final var cave = Cave.of(ResourceLines.list("/day15.txt"));

        Timer.run(() -> part1(cave));
        Timer.run(() -> part2(cave));
    }

    static void part1(Cave cave) {
        final var finish = CaveSearch.bfs(cave.start(), CaveSearch.goalTest(cave), CaveSearch.successors(cave)).orElseThrow();
        System.out.println("the lowest total risk of any path from the top left to the bottom right is " + finish.totalRisk());
    }

    static void part2(Cave cave) {
        final var expanded = cave.expand(5);
        final var finish = CaveSearch.bfs(expanded.start(), CaveSearch.goalTest(expanded), CaveSearch.successors(expanded)).orElseThrow();
        System.out.println("the lowest total risk of any path from the top left to the bottom right in the expanded cave is " + finish.totalRisk());
    }
}
