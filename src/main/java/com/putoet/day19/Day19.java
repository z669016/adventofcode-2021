package com.putoet.day19;

import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day19 {
    public static void main(String[] args) {
        final var scanners = Scanners.of(ResourceLines.list("/day19.txt"));
        final var scannerLocations = Timer.run(() -> part1(scanners));
        Timer.run(() -> part2(scannerLocations));
    }

    static List<Point3D> part1(List<Scanner> scanners) {
        final var locations = Scanners.scannerLocations(scanners);
        final var scannerLocations = locations.getValue1();
        final Scanner baseScanner = locations.getValue0();
        System.out.println("There are " + baseScanner.beacons().size() + " beacons");

        return scannerLocations;
    }

    static void part2(List<Point3D> scannerLocations) {
        System.out.println("There maximum distance between two scanners is " + Scanners.maxDistance(scannerLocations));
    }
}
