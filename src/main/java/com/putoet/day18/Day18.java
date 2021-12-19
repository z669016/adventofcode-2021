package com.putoet.day18;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day18 extends Day {
    private final List<String> input = ResourceLines.list("/day19.txt");

    protected Day18(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day18(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final List<SnailFishNumber> numbers = SnailFishNumbers.of(input);
        SnailFishNumber root = numbers.get(0);
        for (int idx = 1; idx < numbers.size(); idx++) {
            root = root.add(numbers.get(idx));
            root.reduce();
        }
        System.out.println("the magnitude of the final sum is " + root.magnitude());
    }

    @Override
    public void part2() {
        long max = Long.MIN_VALUE;
        for (int a = 0; a < input.size(); a++) {
            for (int b = 0; b < input.size(); b++) {
                if (b == a)
                    continue;

                SnailFishNumber first = SnailFishNumbers.of(input.get(a));
                SnailFishNumber second = SnailFishNumbers.of(input.get(b));
                first = first.add(second);
                first.reduce();
                max = Math.max(max,first.magnitude());

                first = SnailFishNumbers.of(input.get(a));
                second = SnailFishNumbers.of(input.get(b));
                second = second.add(first);
                second.reduce();
                max = Math.max(max,second.magnitude());
            }
        }
        System.out.println("the largest magnitude of any sum of two different snail fish numbers from the homework assignment is " +max);
    }

}
