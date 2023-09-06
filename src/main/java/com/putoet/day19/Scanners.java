package com.putoet.day19;

import com.putoet.grid.Point3D;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class Scanners {
    private Scanners() {
    }

    public static List<Scanner> of(@NotNull List<String> lines) {
        final var scanners = new ArrayList<Scanner>();
        for (var offset = 0; offset < lines.size(); offset++) {
            final var scannerId = lines.get(offset).split(" ")[2];

            offset++;

            final var beacons = new ArrayList<Point3D>();
            while (offset < lines.size() && !lines.get(offset).isEmpty()) {
                beacons.add(point3d(lines.get(offset)));
                offset++;
            }
            scanners.add(new Scanner(scannerId, beacons));
        }

        return scanners;
    }

    private static Point3D point3d(String line) {
        final var split = line.split(",");
        assert split.length == 3;

        return Point3D.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public static Pair<Scanner,List<Point3D>> scannerLocations(@NotNull List<Scanner> scanners) {
        final var baseScanner = scanners.remove(0);
        final var scannerLocations = new ArrayList<Point3D>();
        scannerLocations.add(Point3D.ORIGIN);

        while (!scanners.isEmpty()) {
            outer:
            for (var scanner : scanners) {
                for (var rotation : scanner.rotations()) {
                    final var transformation = baseScanner.findTranslation(rotation.beacons());
                    if (transformation.isPresent()) {
                        scannerLocations.add(transformation.get());
                        baseScanner.add(rotation, transformation.get());
                        scanners.remove(scanner);
                        break outer;
                    }
                }
            }
        }

        return Pair.with(baseScanner,scannerLocations);
    }

    public static int maxDistance(@NotNull List<Point3D> scannerLocations) {
        assert !scannerLocations.isEmpty();

        var max = Integer.MIN_VALUE;
        for (var outer : scannerLocations) {
            for (var inner : scannerLocations) {
                max = Math.max(max, outer.manhattanDistance(inner));
            }
        }

        return max;
    }
}
