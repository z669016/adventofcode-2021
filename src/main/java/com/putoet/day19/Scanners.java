package com.putoet.day19;

import com.putoet.grid.Point3D;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scanners {
    private Scanners() {
    }

    public static List<Scanner> of(List<String> lines) {
        final List<Scanner> scanners = new ArrayList<>();
        for (int offset = 0; offset < lines.size(); offset++) {
            final String scannerId = lines.get(offset).split(" ")[2];

            offset++;

            final List<Point3D> beacons = new ArrayList<>();
            while (offset < lines.size() && lines.get(offset).length() > 0) {
                beacons.add(point3d(lines.get(offset)));
                offset++;
            }
            scanners.add(new Scanner(scannerId, beacons));
        }

        return scanners;
    }

    private static Point3D point3d(String line) {
        assert line != null;

        final String[] split = line.split(",");
        assert split.length == 3;

        return Point3D.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public static Pair<Scanner,List<Point3D>> scannerLocations(List<Scanner> scanners) {
        final Scanner baseScanner = scanners.remove(0);
        final List<Point3D> scannerLocations = new ArrayList<>();
        scannerLocations.add(Point3D.ORIGIN);

        while (scanners.size() > 0) {
            outer:
            for (Scanner scanner : scanners) {
                for (Scanner rotation : scanner.rotations()) {
                    final Optional<Point3D> transformation = baseScanner.findTranslation(rotation.beacons());
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

    public static int maxDistance(List<Point3D> scannerLocations) {
        assert scannerLocations != null;
        assert !scannerLocations.isEmpty();

        int max = Integer.MIN_VALUE;
        for (Point3D outer : scannerLocations) {
            for (Point3D inner : scannerLocations) {
                max = Math.max(max, outer.manhattanDistance(inner));
            }
        }

        return max;
    }
}
