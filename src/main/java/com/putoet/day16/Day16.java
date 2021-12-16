package com.putoet.day16;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

public class Day16 extends Day {
    private final String input;

    protected Day16(String[] args) {
        super(args);

        input = ResourceLines.line("/day16.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day16(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final long count = Calculator.addVersionNumbers(input);
        System.out.println("if you add up the version numbers in all packets you get " + count);
    }

    @Override
    public void part2() {
        final long value = Calculator.compute(input);
        System.out.println("if you evaluate the expression represented by your hexadecimal-encoded BITS transmission you get " + value);
    }
}
