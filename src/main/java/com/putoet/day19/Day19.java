package com.putoet.day19;

import com.putoet.day.Day;
import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;

import java.util.List;

public class Day19 extends Day {
    private final List<Scanner> scanners;
    private List<Point3D> scannerLocations;

    protected Day19(String[] args) {
        super(args);

        scanners = Scanners.of(ResourceLines.list("/day19.txt"));
    }

    public static void main(String[] args) {
        final Day day = new Day19(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Pair<Scanner,List<Point3D>> locations = Scanners.scannerLocations(scanners);
        scannerLocations = locations.getValue1();

        final Scanner baseScanner = locations.getValue0();
        System.out.println("There are " + baseScanner.beacons().size() + " beacons");
    }

    @Override
    public void part2() {
        System.out.println("There maximum distance between two scanners is " + Scanners.maxDistance(scannerLocations));
    }
}
