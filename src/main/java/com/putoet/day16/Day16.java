package com.putoet.day16;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day16 {
    public static void main(String[] args) {
        final var input = ResourceLines.line("/day16.txt");

        Timer.run(() -> part1(input));
        Timer.run(() -> part2(input));
    }

    static void part1(String input) {
        final var count = Calculator.addVersionNumbers(input);
        System.out.println("if you add up the version numbers in all packets you get " + count);
    }

    static void part2(String input) {
        final var value = Calculator.compute(input);
        System.out.println("if you evaluate the expression represented by your hexadecimal-encoded BITS transmission you get " + value);
    }
}
