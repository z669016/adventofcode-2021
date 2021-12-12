package com.putoet.day5;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

public class Day5 extends Day {
    protected Day5(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day5(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final OceanFloor oceanFloor = OceanFloor.of(ResourceLines.list("/day5.txt"),
                line -> line.start().x() == line.end().x() || line.start().y() == line.end().y());

        System.out.println("Overlap of single-line vents is " + oceanFloor.overlap());
    }

    @Override
    public void part2() {
        final OceanFloor oceanFloor = OceanFloor.of(ResourceLines.list("/day5.txt"), line -> true);

        System.out.println("Overlap of single-line and diagonal vents is " + oceanFloor.overlap());
    }
}
