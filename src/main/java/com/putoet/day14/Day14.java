package com.putoet.day14;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;

import java.util.Map;

public class Day14 extends Day {
    private final PairInsertionRules insertionRules;

    protected Day14(String[] args) {
        super(args);
        insertionRules = PairInsertionRules.of(ResourceLines.list("/day14.txt"));
    }

    public static void main(String[] args) {
        final Day day = new Day14(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Pair<String, Map<String, Long>> transformed = insertionRules.transform(10);
        final Pair<String, Long> mostCommon = insertionRules.mostCommonElement(transformed);
        final Pair<String, Long> leastCommon = insertionRules.leastCommonElement(transformed);

        System.out.println("if you take the quantity of the most common element and subtract the quantity of the least common element you get " +
                (mostCommon.getValue1() - leastCommon.getValue1()));

    }

    @Override
    public void part2() {
        final Pair<String, Map<String, Long>> transformed = insertionRules.transform(40);
        final Pair<String, Long> mostCommon = insertionRules.mostCommonElement(transformed);
        final Pair<String, Long> leastCommon = insertionRules.leastCommonElement(transformed);

        System.out.println("if you take the quantity of the most common element and subtract the quantity of the least common element you get " +
                (mostCommon.getValue1() - leastCommon.getValue1()));

    }
}
