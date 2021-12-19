package com.putoet.day19;

import com.putoet.grid.Point3D;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transformer {
    private static final List<Function<Point3D, Point3D>> transformXFunctions = List.of(
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), p.z(), p.y()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), p.y(), -p.z()),
            (Point3D p) -> Point3D.of(-p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(-p.x(), p.z(), p.y()),
            (Point3D p) -> Point3D.of(-p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(-p.x(), p.y(), -p.z())
    );
    private static final List<Function<Point3D, Point3D>> transformYFunctions = List.of(
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.z(), p.y(), p.x()),
            (Point3D p) -> Point3D.of(-p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), p.y(), -p.z()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.z(), -p.y(), p.x()),
            (Point3D p) -> Point3D.of(-p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), -p.z())
    );
    private static final List<Function<Point3D, Point3D>> transformZFunctions = List.of(
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.y(), p.x(), p.z()),
            (Point3D p) -> Point3D.of(-p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.y(), p.x(), p.x()),
            (Point3D p) -> Point3D.of(-p.x(), p.y(), p.z()),
            (Point3D p) -> Point3D.of(p.x(), -p.y(), p.z())
    );

    public static Optional<Set<Point3D>> isTransformed(Set<Point3D> from, Set<Point3D> to, int overlap) {
        int i = 0;
        for (Function<Point3D, Point3D> transformXFunction : transformXFunctions) {
            for (Function<Point3D, Point3D> transformYFunction : transformYFunctions) {
                for (Function<Point3D, Point3D> transformZFunction : transformZFunctions) {
                    final Set<Point3D> transformed = to.stream()
                            .map(transformXFunction)
                            .map(transformYFunction)
                            .map(transformZFunction)
                            .collect(Collectors.toSet());

                    if (intersection(from, transformed).size() >= overlap)
                        return Optional.of(transformed);
                }
            }
        }
        return Optional.empty();
    }

    private static Set<Point3D> intersection(Set<Point3D> from, Set<Point3D> transformed) {
        final Set<Point3D> intersection = new HashSet<>(from);
        intersection.retainAll(transformed);
        return intersection;
    }
}
