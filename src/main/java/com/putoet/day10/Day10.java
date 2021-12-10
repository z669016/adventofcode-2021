package com.putoet.day10;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day10 extends Day {
    private final List<String> input;

    protected Day10(String[] args) {
        super(args);
        input = ResourceLines.list("/day10.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day10(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final List<String> corrupted = Chunk.corrupted(input);
        System.out.println("The total syntax error score for those errors is "+ Chunk.corruptedScore(corrupted));
    }

    @Override
    public void part2() {
        final List<String> uncompleted = Chunk.incomplete(input);
        System.out.println("The total syntax error score for those errors is "+ Chunk.incompleteScore(uncompleted));
    }
}
