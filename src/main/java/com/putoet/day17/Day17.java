package com.putoet.day17;

import com.putoet.day.Day;
import com.putoet.grid.Point;

import java.util.List;
import java.util.Optional;

public class Day17 extends Day {
    private final Point topLeft = Point.of(195, -67);
    private final Point bottomRight = Point.of(238, -93);
    private final ProbeLauncher launcher = new ProbeLauncher(topLeft, bottomRight);

    protected Day17(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day17(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final List<Point> hits = launcher.hits();
        final Optional<Point> highest = launcher.highest(hits);
        if (highest.isEmpty())
            System.out.println("No highest point found");
        else {
            int height = launcher.maxHeight(highest.get());
            System.out.println("the highest point was at Y is " + height);
        }
    }

    @Override
    public void part2() {
        final List<Point> hits = launcher.hits();
        System.out.println("the number of distinct initial velocity values is " + hits.size());
    }
}
