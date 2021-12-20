package com.putoet.day19;

import com.putoet.grid.Point3D;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transformer {
    private static final List<Function<Point3D, Point3D>> transformXFunctions = List.of(
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), p.z(), p.y()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), p.y(), -p.z()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), -p.z()),
            (Point3D p) -> Point3D.of(p.x(), -p.z(), -p.y()),

            (Point3D p) -> Point3D.of(-p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(-p.x(), p.z(), p.y()),
            (Point3D p) -> Point3D.of(-p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(-p.x(), p.y(), -p.z()),
            (Point3D p) -> Point3D.of(-p.x(), -p.y(), -p.z()),
            (Point3D p) -> Point3D.of(-p.x(), -p.z(), -p.y())
    );
    private static final List<Function<Point3D, Point3D>> transformYFunctions = List.of(
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),

            (Point3D p) -> Point3D.of(p.z(), p.y(), p.x()),
            (Point3D p) -> Point3D.of(-p.z(), p.y(), p.x()),
            (Point3D p) -> Point3D.of(p.z(), p.y(), -p.x()),
            (Point3D p) -> Point3D.of(-p.z(), p.y(), -p.x()),

            (Point3D p) -> Point3D.of(p.z(), -p.y(), p.x()),
            (Point3D p) -> Point3D.of(-p.z(), -p.y(), p.x()),
            (Point3D p) -> Point3D.of(p.z(), -p.y(), -p.x()),
            (Point3D p) -> Point3D.of(-p.z(), -p.y(), -p.x())
    );
    private static final List<Function<Point3D, Point3D>> transformZFunctions = List.of(
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),

            (Point3D p) -> Point3D.of(p.y(), p.x(), p.z()),
            (Point3D p) -> Point3D.of(-p.y(), p.x(), p.z()),
            (Point3D p) -> Point3D.of(p.y(), -p.x(), p.z()),
            (Point3D p) -> Point3D.of(-p.y(), -p.x(), p.z()),

            (Point3D p) -> Point3D.of(p.y(), p.x(), -p.z()),
            (Point3D p) -> Point3D.of(-p.y(), p.x(), -p.z()),
            (Point3D p) -> Point3D.of(p.y(), -p.x(), -p.z()),
            (Point3D p) -> Point3D.of(-p.y(), -p.x(), -p.z())
    );

    public static List<Scanner> transform(List<Scanner> scanners) {
        final Scanner[] transformedScanners = new Scanner[scanners.size()];
        transformedScanners[0] = scanners.get(0);

        boolean didTransform = true;
        while (didTransform) {
            didTransform = false;

            for (int toIndex = 0; toIndex < scanners.size(); toIndex++) {
                if (transformedScanners[toIndex] != null)
                    continue;

                for (int fromIndex = 0; fromIndex < transformedScanners.length; fromIndex++) {
                    if (transformedScanners[fromIndex] == null)
                        continue;

                    final Scanner from = transformedScanners[fromIndex];
                    final Optional<Set<Point3D>> transformed =
                            Transformer.isTransformed(from, scanners.get(toIndex), 12);

                    if (transformed.isPresent()) {
                        System.out.println("Found " + toIndex);
                        transformedScanners[toIndex] = new Scanner(scanners.get(toIndex).id(), transformed.get());
                        didTransform = true;
                        break;
                    }

                    if (didTransform)
                        break;
                }
            }
        }

        return Arrays.asList(transformedScanners);
    }

    public static Optional<Set<Point3D>> isTransformed(Scanner from, Scanner to, int overlap) {
        for (Function<Point3D, Point3D> transformXFunction : transformXFunctions) {
            for (Function<Point3D, Point3D> transformYFunction : transformYFunctions) {
                for (Function<Point3D, Point3D> transformZFunction : transformZFunctions) {
                    final Set<Point3D> transformed = to.beacons().stream()
                            .map(transformXFunction)
                            .map(transformYFunction)
                            .map(transformZFunction)
                            .collect(Collectors.toSet());

                    final Map<Point3D, List<Point3D>> distanceMap = distanceMap(from.beacons(), transformed);
                    final List<Point3D> vectors = overlap(distanceMap, overlap);
                    if (vectors.size() > 0) {
                        final Point3D vector = vectors.get(0);
                        final Set<Point3D> moved = transformed.stream()
                                .map(point -> point.add(vector))
                                .collect(Collectors.toSet());
                        return Optional.of(moved);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static List<Point3D> overlap(Map<Point3D, List<Point3D>> distanceMap, int overlap) {
        final Map<Point3D, Long> distanceCount = distanceMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        return distanceCount.entrySet().stream()
                .filter(entry -> entry.getValue() >= overlap)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static Map<Point3D, List<Point3D>> distanceMap(Set<Point3D> from, Set<Point3D> to) {
        return from.stream()
                .map(a -> Pair.with(a, to.stream().map(b -> distance(a, b)).toList()))
                .collect(Collectors.toMap(Pair::getValue0, Pair::getValue1));
    }

    public static Point3D distance(Point3D a, Point3D b) {
        return Point3D.of(a.x() - b.x(), a.y() - b.y(), a.z() - b.z());
    }

    private static Set<Point3D> intersection(Set<Point3D> from, Set<Point3D> transformed) {
        final Set<Point3D> intersection = new HashSet<>(from);
        intersection.retainAll(transformed);
        return intersection;
    }
}
