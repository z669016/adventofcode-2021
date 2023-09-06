package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day18 {
    public static void main(String[] args) {
        final List<String> input = ResourceLines.list("/day18.txt");

        Timer.run(() -> part1(input));
        Timer.run(() -> part2(input));
    }

    static void part1(List<String> input) {
        final var numbers = SnailFishNumbers.of(input);
        var root = numbers.get(0);
        for (var idx = 1; idx < numbers.size(); idx++) {
            root = root.add(numbers.get(idx));
            root.reduce();
        }
        System.out.println("the magnitude of the final sum is " + root.magnitude());
    }

    static void part2(List<String> input) {
        var max = Long.MIN_VALUE;
        for (var a = 0; a < input.size(); a++) {
            for (var b = 0; b < input.size(); b++) {
                if (b == a)
                    continue;

                var first = SnailFishNumbers.of(input.get(a));
                var second = SnailFishNumbers.of(input.get(b));
                first = first.add(second);
                first.reduce();
                max = Math.max(max, first.magnitude());

                first = SnailFishNumbers.of(input.get(a));
                second = SnailFishNumbers.of(input.get(b));
                second = second.add(first);
                second.reduce();
                max = Math.max(max, second.magnitude());
            }
        }
        System.out.println("the largest magnitude of any sum of two different snail fish numbers from the homework assignment is " + max);
    }
}
