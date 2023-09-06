package com.putoet.day19;

import com.putoet.grid.Point3D;
import org.jetbrains.annotations.NotNull;

import java.util.*;

class Scanner {
    private final String id;
    private final List<Point3D> beacons;
    private List<Scanner> rotations;

    public Scanner(@NotNull String id, @NotNull List<Point3D> beacons) {
        this.id = id;
        this.beacons = beacons;
    }

    public static List<Scanner> rotations(@NotNull String id, @NotNull List<Point3D> beacons) {
        final var rotations = new ArrayList<Scanner>();

        for (var i = 0; i < 24; i++)
            rotations.add(new Scanner(id + "("+ i + ")", new ArrayList<>()));

        for (var point : beacons) {
            final var pointRotations = Point3D.rotations(point);
            for (var i = 0; i < 24; i++)
                rotations.get(i).beacons().add(pointRotations.get(i));
        }

        return rotations;
    }

    public List<Point3D> beacons() {
        return beacons;
    }

    public List<Scanner> rotations() {
        if (rotations == null)
            rotations = rotations(id, beacons);

        return rotations;
    }

    public Optional<Point3D> findTranslation(List<Point3D> rotatedBeacons) {
        final var map = new HashMap<Point3D, Integer>();
        for (var beacon : beacons) {
            for (var rotatedBeacon : rotatedBeacons) {
                final var diff = beacon.sub(rotatedBeacon);
                map.merge(diff, 1, Integer::sum);
            }
        }

        final var matches = map.entrySet().stream()
                .filter(entry -> entry.getValue() >= 12)
                .map(Map.Entry::getKey)
                .toList();
        if (matches.size() > 1) {
            throw new IllegalStateException("Multiple matches for scanner " + id);
        }
        return matches.isEmpty() ? Optional.empty() : Optional.of(matches.get(0));
    }

    public void add(Scanner other) {
        for (var point : other.beacons)
            if (!beacons.contains(point)) beacons.add(point);
    }

    public void add(@NotNull Scanner other, @NotNull Point3D trans) {
        for (var point : other.beacons) {
            point = point.add(trans);
            if (!beacons.contains(point)) beacons.add(point);
        }
    }
}
