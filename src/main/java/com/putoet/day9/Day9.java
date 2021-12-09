package com.putoet.day9;

import com.putoet.day.Day;
import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.Set;

public class Day9 extends Day {
    private final HeightMap heightMap;

    protected Day9(String[] args) {
        super(args);
        heightMap = HeightMap.of(ResourceLines.list("/day9.txt"));
    }

    public static void main(String[] args) {
        final Day day = new Day9(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final List<Point> lowest = heightMap.lowest();
        System.out.println("The risk level for the height map is " + heightMap.riskLevel(lowest));
    }

    @Override
    public void part2() {
        final List<Set<Point>> largest = heightMap.largestBasins();

        System.out.println("Score for the 3 largest asins is "
                + largest.get(0).size() * largest.get(1).size() * largest.get(2).size());
    }
}
