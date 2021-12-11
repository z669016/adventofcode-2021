package com.putoet.day11;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day11 extends Day {
    private final List<String> input;

    protected Day11(String[] args) {
        super(args);
        input = ResourceLines.list("/day11.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day11(args);
        day.challenge();
    }

    @Override
    public void part1() {
        int count = 0;
        final Cavern cavern = Cavern.of(input);
        for (int i = 0; i < 100; i++)
            count += cavern.step();

        System.out.println("total flashes after 100 steps is " + count);
    }

    @Override
    public void part2() {
        int count = 1;
        final Cavern cavern = Cavern.of(input);
        while (cavern.step() != cavern.size())
            count++;

        System.out.println("the first step during which all octopuses flash is " + count);
    }
}
