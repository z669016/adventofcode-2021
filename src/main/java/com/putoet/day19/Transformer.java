package com.putoet.day19;

import com.putoet.grid.Point3D;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transformer {
    public static final int ROTATION_COUNT = 4;
    public static final int FLIP_COUNT = 2;

    private static final Function<Point3D, Point3D> rotateAroundXAxis = p -> Point3D.of(p.x(), p.z(), -p.y())
    private static final Function<Point3D, Point3D> rotateAroundYAxis = p -> Point3D.of(p.z(), p.y(), -p.x());
    private static final Function<Point3D, Point3D> rotateAroundZAxis = p -> Point3D.of(p.y(), -p.x(), p.z());
    private static final Function<Point3D, Point3D> flipX = p -> Point3D.of(-p.x(), p.y(), p.z());
    private static final Function<Point3D, Point3D> flipY = p -> Point3D.of(p.x(), -p.y(), p.z());
    private static final Function<Point3D, Point3D> flipZ = p -> Point3D.of(-p.x(), p.y(), -p.z());
    private static final Function<Point3D, Point3D> nope = p -> p;

    private static List<Scanner> createTransformedScanners(List<Scanner> scanners) {
        final Map<Integer, List<Scanner>> transformedScanners = new HashMap<>();
        transformedScanners.put(scanners.get(0).id(), new ArrayList<>());
        transformedScanners.get(scanners.get(0).id()).add(scanners.get(0));

        for (Scanner scanner : scanners) {
            final List<Scanner> transformed = new ArrayList<>();
            for (int zRotation = 0; zRotation < ROTATION_COUNT; zRotation++) {
                for (int yRotation = 0; yRotation < ROTATION_COUNT; yRotation++) {
                    for (int xRotation = 0; xRotation < ROTATION_COUNT; xRotation++) {
                        for (int zFlip = 0; zFlip < FLIP_COUNT; zFlip++) {
                            for (int yFlip = 0; yFlip < FLIP_COUNT; yFlip++) {
                                for (int xFlip = 0; xFlip < FLIP_COUNT; xFlip++) {
                                    transformed.add(transformScanner(scanner, zRotation, yRotation,xRotation,zFlip,yFlip,xFlip));
                                }
                            }
                        }
                    }
                }
            }
            transformedScanners.put(scanner.id(), transformed);
        }

    }

    private static Scanner transformScanner(Scanner scanner, int zRotation, int yRotation, int xRotation, int zFlip, int yFlip, int xFlip) {
        return new Scanner(scanner.id(), scanner.beacons().stream()
                .map(p -> {
                    for (int i = 0; i < zRotation; i++)
                        p = rotateAroundZAxis.apply(p);
                    return p;
                })
                .map(p -> {
                    for (int i = 0; i < yRotation; i++)
                        p = rotateAroundYAxis.apply(p);
                    return p;
                })
                .map(p -> {
                    for (int i = 0; i < xRotation; i++)
                        p = rotateAroundXAxis.apply(p);
                    return p;
                })
                .map(p -> {
                    for (int i = 0; i < zFlip; i++)
                        p = flipZ.apply(p);
                    return p;
                })
                .map(p -> {
                    for (int i = 0; i < yFlip; i++)
                        p = flipY.apply(p);
                    return p;
                })
                .map(p -> {
                    for (int i = 0; i < xFlip; i++)
                        p = flipX.apply(p);
                    return p;
                })
                .collect(Collectors.toSet())
        );
    }

    public static List<Scanner> transform(List<Scanner> scanners) {
        final Map<Integer, List<Scanner> transformedScanners = new HashMap<>();
        transformedScanners.put(scanners.get(0).id(), new ArrayList<>());
        transformedScanners.get(scanners.get(0).id()).add(scanners.get(0));

        /


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

    private List<Function<Point3D, Point3D>> transformationTable() {
        final List<Function<Point3D, Point3D>> transformationTable = new ArrayList<>();

        Function<Point3D, Point3D> transformation = nope;
        for (int zRotation = 0; zRotation < ROTATION_COUNT; zRotation++) {
            transformation = transformation.andThen(rotateAroundZAxis);
            for (int yRotation = 0; yRotation < ROTATION_COUNT; yRotation++) {
                for (int xRotation = 0; xRotation < ROTATION_COUNT; xRotation++) {
                    for (int zFlip = 0; zFlip < FLIP_COUNT; zFlip++) {
                        for (int yFlip = 0; yFlip < FLIP_COUNT; yFlip++) {
                            for (int xFlip = 0; xFlip < FLIP_COUNT; xFlip++) {

                            }
                        }
                    }
                }
            }
        }

        return transformationTable;
    }
}
