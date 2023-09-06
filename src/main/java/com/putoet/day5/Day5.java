package com.putoet.day5;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        final var vents = Vent.of(ResourceLines.list("/day5.txt"));

        Timer.run(() -> part1(vents));
        Timer.run(() -> part2(vents));
    }

    static void part1(List<Vent> vents) {
        final var oceanFloor = OceanFloor.of(vents,
                line -> line.start().x() == line.end().x() || line.start().y() == line.end().y());

        System.out.println("Overlap of single-line vents is " + oceanFloor.overlap());
    }

    static void part2(List<Vent> vents) {
        final var oceanFloor = OceanFloor.of(vents, line -> true);

        System.out.println("Overlap of single-line and diagonal vents is " + oceanFloor.overlap());
    }
}
