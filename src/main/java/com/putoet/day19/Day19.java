package com.putoet.day19;

import com.putoet.day.Day;
import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day19 extends Day {
    private final List<String> input = ResourceLines.list("/day19.txt");
    private final List<Scanner> scanners = new ArrayList<>(Scanners.of(ResourceLines.list("/day19.txt")));
    private List<Scanner> transformed;

    protected Day19(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day19(args);
        day.challenge();
    }

    @Override
    public void part1() {
        transformed = Transformer.transform(scanners);
        final Set<Point3D> beacons = Scanners.beacons(transformed);

        transformed.stream()
                .filter(Objects::nonNull)
                .forEach(s -> System.out.println(s.id()));

        System.out.println(beacons.size() + " beacons found");
    }
}
