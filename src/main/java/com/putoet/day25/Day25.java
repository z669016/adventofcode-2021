package com.putoet.day25;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;

public class Day25 extends Day {
    private final SeaFloor initial;

    protected Day25(String[] args) {
        super(args);
        initial = SeaFloor.of(ResourceLines.list("/day25.txt"));
    }

    public static void main(String[] args) {
        final Day day = new Day25(args);
        day.challenge();
    }

    @Override
    public void part1() {
        int step = 1;
        Pair<SeaFloor, Integer> next = initial.step();
        while (next.getValue1() != 0) {
            step++;
            next = next.getValue0().step();
        }

        System.out.println("the first step on which no sea cucumbers move is " + step);
    }

    @Override
    public void part2() {
        // Day 25 part 2 is a free ride !!!!
    }
}
