package com.putoet.day19;

import com.putoet.grid.Point3D;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public record Scanner(int id, Set<Point3D> beacons) {
    public static Point3D distance(Point3D a, Point3D b) {
        return Point3D.of(a.x() - b.x(), a.y() - b.y(), a.z() - b.z());
    }

    public static boolean overlap(Map<Point3D, List<Point3D>> distanceMap, int overlap) {
        final Map<Point3D,Long> distanceCount = distanceMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        return distanceCount.values().stream().anyMatch(count -> count >= overlap);
    }

    public Map<Point3D, List<Point3D>> distanceMap(Scanner to) {
        return beacons.stream()
                .map(a -> Pair.with(a, to.beacons.stream().map(b -> distance(a, b)).toList()))
                .collect(Collectors.toMap(Pair::getValue0, Pair::getValue1));
    }


}
