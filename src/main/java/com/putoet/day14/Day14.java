package com.putoet.day14;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day14  {
    public static void main(String[] args) {
        final var insertionRules = PairInsertionRules.of(ResourceLines.list("/day14.txt"));

        Timer.run(() -> part1(insertionRules));
        Timer.run(() -> part2(insertionRules));
    }

    static void part1(PairInsertionRules insertionRules) {
        final var transformed = insertionRules.transform(10);
        final var mostCommon = insertionRules.mostCommonElement(transformed);
        final var leastCommon = insertionRules.leastCommonElement(transformed);

        System.out.println("if you take the quantity of the most common element and subtract the quantity of the least common element you get " +
                (mostCommon.getValue1() - leastCommon.getValue1()));

    }

    static void part2(PairInsertionRules insertionRules) {
        final var transformed = insertionRules.transform(40);
        final var mostCommon = insertionRules.mostCommonElement(transformed);
        final var leastCommon = insertionRules.leastCommonElement(transformed);

        System.out.println("if you take the quantity of the most common element and subtract the quantity of the least common element you get " +
                (mostCommon.getValue1() - leastCommon.getValue1()));
    }
}
