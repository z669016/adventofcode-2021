package com.putoet.day22;

import com.putoet.grid.Point3D;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Range3D(Point3D min, Point3D max) {
    public static final Pattern LINE_PATTERN = Pattern.compile("x=([-+]?\\d+)..([-+]?\\d+),y=([-+]?\\d+)..([-+]?\\d+),z=([-+]?\\d+)..([-+]?\\d+)");
    public static final int MIN_X = 1;
    public static final int MAX_X = 2;
    public static final int MIN_Y = 3;
    public static final int MAX_Y = 4;
    public static final int MIN_Z = 5;
    public static final int MAX_Z = 6;

    public Range3D {
        assert min.x() <= max.x();
        assert min.y() <= max.y();
        assert min.z() <= max.z();
    }

    public static List<Range3D> of(List<String> lines) {
        return lines.stream().map(Range3D::of).toList();
    }

    public static Range3D of(String line) {
        assert line != null;

        final Matcher matcher = LINE_PATTERN.matcher(line);
        if (!matcher.matches())
            throw new IllegalArgumentException("Invalid line " + line);

        return new Range3D(
                Point3D.of(Integer.parseInt(matcher.group(MIN_X)),
                        Integer.parseInt(matcher.group(MIN_Y)),
                        Integer.parseInt(matcher.group(MIN_Z))
                ),
                Point3D.of(Integer.parseInt(matcher.group(MAX_X)),
                        Integer.parseInt(matcher.group(MAX_Y)),
                        Integer.parseInt(matcher.group(MAX_Z))
                )
        );
    }

    public static Range3D of(Point3D from, Point3D to) {
        return new Range3D(from, to);
    }

    public static long point3DCount(Range3D range) {
        return (long) Math.abs(1 + range.max.x() - range.min.x()) *
                Math.abs(1 + range.max.y() - range.min.y()) *
                Math.abs(1 + range.max.z() - range.min.z());
    }

    public static List<Range3D> carve(Range3D toSplit, Range3D other) {
        final List<Range3D> split = new ArrayList<>();

        if (!toSplit.overlap(other))
            return List.of(toSplit);

        // 1 - When the split has overlap to the left of the current command according to the X-axix
        if (toSplit.min().x() <= other.min().x() && other.min().x() <= toSplit.max().x()) {
            // Add block that ends at the minX of the next command range
            final Optional<Range3D> temp = toSplit.maxX(other.min().x() - 1);
            if (temp.isPresent()) {
                split.add(temp.get());
                toSplit = toSplit.minX(other.min().x()).get();
            }
        }

        // 2 - When the split has overlap to the right of the current command according to the X-axix
        if (toSplit.min().x() <= other.max().x() && other.max().x() <= toSplit.max().x()) {
            // Add block that ends at the minX of the next command range
            final Optional<Range3D> temp = toSplit.minX(other.max().x() + 1);
            if (temp.isPresent()) {
                split.add(temp.get());
                toSplit = toSplit.maxX(other.max().x()).get();
            }
        }

        // 3 - When the split has overlap below of the current command according to the Y-axix
        if (toSplit.min().y() <= other.min().y() && other.min().y() <= toSplit.max().y()) {
            // Add block that ends at the minX of the next command range
            final Optional<Range3D> temp = toSplit.maxY(other.min().y() - 1);
            if (temp.isPresent()) {
                split.add(temp.get());
                toSplit = toSplit.minY(other.min().y()).get();
            }
        }

        // 4 -  When the split has overlap above the current command according to the Y-axix
        if (toSplit.min().y() <= other.max().y() && other.max().y() <= toSplit.max().y()) {
            // Add block that ends at the minX of the next command range
            final Optional<Range3D> temp = toSplit.minY(other.max().y() + 1);
            if (temp.isPresent()) {
                split.add(temp.get());
                toSplit = toSplit.maxY(other.max().y()).get();
            }
        }

        // 5 - When the split has overlap in front of the current command according to the Z-axix
        if (toSplit.min().z() <= other.min().z() && other.min().z() <= toSplit.max().z()) {
            // Add block that ends at the minX of the next command range
            final Optional<Range3D> temp = toSplit.maxZ(other.min().z() - 1);
            if (temp.isPresent()) {
                split.add(temp.get());
                toSplit = toSplit.minZ(other.min().z()).get();
            }
        }

        // 6 -  When the split has overlap behind the current command according to the Z-axix
        if (toSplit.min().z() <= other.max().z() && other.max().z() <= toSplit.max().z()) {
            // Add block that ends at the minX of the next command range
            final Optional<Range3D> temp = toSplit.minZ(other.max().z() + 1);
            if (temp.isPresent()) {
                split.add(temp.get());
                toSplit = toSplit.maxZ(other.max().z()).get();
            }
        }
        return split;
    }

    public boolean overlap(Range3D other) {
        return (min.x() <= other.min.x() && other.min.x() <= max.x() || other.min.x() <= min.x() && min.x() <= other.max.x()) &&
                (min.y() <= other.min.y() && other.min.y() <= max.y() || other.min.y() <= min.y() && min.y() <= other.max.y()) &&
                (min.z() <= other.min.z() && other.min.z() <= max.z() || other.min.z() <= min.z() && min.z() <= other.max.z());
    }

    public boolean contains(Range3D other) {
        return (min.x() <= other.min.x() && max.x() >= other.max.x()) &&
                (min.y() <= other.min.y() && max.y() >= other.max.y()) &&
                (min.z() <= other.min.z() && max.z() >= other.max.z());
    }

    public long point3DCount() {
        return point3DCount(this);
    }

    public Set<Point3D> toSet() {
        return IntStream.range(min.x(), max.x() + 1).mapToObj(x ->
                        IntStream.range(min.y(), max.y() + 1).mapToObj(y ->
                                IntStream.range(min.z(), max.z() + 1).mapToObj(z -> Point3D.of(x, y, z)).collect(Collectors.toSet())
                        ).flatMap(Collection::stream).collect(Collectors.toSet()))
                .flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public List<Range3D> carve(Range3D other) {
        return carve(this, other);
    }

    public Optional<Range3D> minX(int minX) {
        return Optional.ofNullable(minX <= max.x() ? new Range3D(Point3D.of(minX, min.y(), min.z()), max) : null);
    }

    public Optional<Range3D> minY(int minY) {
        return Optional.ofNullable(minY <= max.y() ? new Range3D(Point3D.of(min.x(), minY, min.z()), max) : null);
    }

    public Optional<Range3D> minZ(int minZ) {
        return Optional.ofNullable(minZ <= max.z() ? new Range3D(Point3D.of(min.x(), min.y(), minZ), max) : null);
    }

    public Optional<Range3D> maxX(int maxX) {
        return Optional.ofNullable(min.x() <= maxX ? new Range3D(min, Point3D.of(maxX, max.y(), max.z())) : null);
    }

    public Optional<Range3D> maxY(int maxY) {
        return Optional.ofNullable(min.y() <= maxY ? new Range3D(min, Point3D.of(max.x(), maxY, max.z())) : null);
    }

    public Optional<Range3D> maxZ(int maxZ) {
        return Optional.ofNullable(min.z() <= maxZ ? new Range3D(min, Point3D.of(max.x(), max.y(), maxZ)) : null);
    }

    @Override
    public String toString() {
        return String.format("x=%d..%d,y=%d..%d,z=%d..%d", min.x(), max.x(), min.y(), max.y(), min.z(), max.z());
    }
}
