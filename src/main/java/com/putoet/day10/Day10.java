package com.putoet.day10;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day10  {
    public static void main(String[] args) {
        final var input = ResourceLines.list("/day10.txt");

        Timer.run(() -> part1(input));
        Timer.run(() -> part2(input));
    }

    static void part1(List<String> input) {
        final var corrupted = Chunk.corrupted(input);
        System.out.println("The total syntax error score for those errors is "+ Chunk.corruptedScore(corrupted));
    }

    static void part2(List<String> input) {
        final var uncompleted = Chunk.incomplete(input);
        System.out.println("The total syntax error score for those errors is "+ Chunk.incompleteScore(uncompleted));
    }
}
