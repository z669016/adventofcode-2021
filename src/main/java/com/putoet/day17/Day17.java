package com.putoet.day17;

import com.putoet.grid.Point;
import com.putoet.utils.Timer;

public class Day17 {
    public static void main(String[] args) {
        final var topLeft = Point.of(195, -67);
        final var bottomRight = Point.of(238, -93);
        final var launcher = new ProbeLauncher(topLeft, bottomRight);

        Timer.run(() -> part1(launcher));
        Timer.run(() -> part2(launcher));
    }

    static void part1(ProbeLauncher launcher) {
        final var hits = launcher.hits();
        final var highest = launcher.highest(hits).orElseThrow();
        final int height = launcher.maxHeight(highest);
        System.out.println("the highest point was at Y is " + height);
    }

    static void part2(ProbeLauncher launcher) {
        final var hits = launcher.hits();
        System.out.println("the number of distinct initial velocity values is " + hits.size());
    }
}
