package com.putoet.day9;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day9 {
    public static void main(String[] args) {
        final var heightMap = HeightMap.of(ResourceLines.list("/day9.txt"));

        Timer.run(() -> part1(heightMap));
        Timer.run(() -> part2(heightMap));
    }

    static void part1(HeightMap heightMap) {
        final var lowest = heightMap.lowest();
        System.out.println("The risk level for the height map is " + heightMap.riskLevel(lowest));
    }

    static void part2(HeightMap heightMap) {
        final var largest = heightMap.largestBasins();

        System.out.println("Score for the 3 largest asins is "
                           + largest.get(0).size() * largest.get(1).size() * largest.get(2).size());
    }
}
