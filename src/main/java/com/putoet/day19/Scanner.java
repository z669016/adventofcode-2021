package com.putoet.day19;

import com.putoet.grid.Point3D;

import java.util.*;

public class Scanner {
    private final String id;
    private final List<Point3D> beacons;
    private List<Scanner> rotations;

    public Scanner(String id, List<Point3D> beacons) {
        this.id = id;
        this.beacons = beacons;
    }

    public static List<Scanner> rotations(String id, List<Point3D> beacons) {
        final List<Scanner> rotations = new ArrayList<>();

        for (int i = 0; i < 24; i++)
            rotations.add(new Scanner(id + "("+ i + ")", new ArrayList<>()));

        for (Point3D point : beacons) {
            final List<Point3D> pointRotations = Point3D.rotations(point);
            for (int i = 0; i < 24; i++)
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
        final Map<Point3D, Integer> map = new HashMap<>();
        for (Point3D beacon : beacons) {
            for (Point3D rotatedBeacon : rotatedBeacons) {
                final Point3D diff = beacon.sub(rotatedBeacon);
                map.merge(diff, 1, Integer::sum);
            }
        }

        final List<Point3D> matches = map.entrySet().stream()
                .filter(entry -> entry.getValue() >= 12)
                .map(Map.Entry::getKey)
                .toList();
        if (matches.size() > 1) {
            throw new IllegalStateException("Multiple matches for scanner " + id);
        }
        return matches.size() == 0 ? Optional.empty() : Optional.of(matches.get(0));
    }

    public void add(Scanner other) {
        for (Point3D point : other.beacons)
            if (!beacons.contains(point)) beacons.add(point);
    }

    public void add(Scanner other, Point3D trans) {
        for (Point3D point : other.beacons) {
            point = point.add(trans);
            if (!beacons.contains(point)) beacons.add(point);
        }
    }
}
