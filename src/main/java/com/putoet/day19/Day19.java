package com.putoet.day19;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day19 extends Day {
    private final List<String> input = ResourceLines.list("/day19.txt");

    protected Day19(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day19(args);
        day.challenge();
    }
}
