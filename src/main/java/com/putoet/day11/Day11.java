package com.putoet.day11;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        final List<String> input = ResourceLines.list("/day11.txt");

        Timer.run(() -> part1(input));
        Timer.run(() -> part2(input));
    }

    static void part1(List<String> input) {
        var count = 0;
        final var cavern = Cavern.of(input);
        for (var i = 0; i < 100; i++)
            count += cavern.step();

        System.out.println("total flashes after 100 steps is " + count);
    }

    static void part2(List<String> input) {
        var count = 1;
        final var cavern = Cavern.of(input);
        while (cavern.step() != cavern.size())
            count++;

        System.out.println("the first step during which all octopuses flash is " + count);
    }
}
