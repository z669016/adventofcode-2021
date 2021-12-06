package com.putoet.day6;

import com.putoet.day.Day;
import com.putoet.resources.CSV;

import java.util.List;

public class Day6 extends Day {
    private final List<Integer> school;

    protected Day6(String[] args) {
        super(args);

        school = CSV.flatList("/day6.txt", Integer::parseInt);
    }

    public static void main(String[] args) {
        final Day day = new Day6(args);
        day.challenge();
    }

    @Override
    public void part1() {
        System.out.println("After 80 days the school has " + LanternFishModel.progress(school, 80) + " fish.");
    }

    @Override
    public void part2() {
        System.out.println("After 256 days the school has " + LanternFishModel.progress(school, 256) + " fish.");
    }
}
