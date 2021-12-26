package com.putoet.day22;

import com.putoet.grid.Point;
import com.putoet.grid.Point3D;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public static Range3D range(List<Range3D> ranges) {
        assert ranges != null;
        assert !ranges.isEmpty();

        int min_x = Integer.MAX_VALUE, min_y = Integer.MAX_VALUE, min_z = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE, max_y = Integer.MIN_VALUE, max_z = Integer.MIN_VALUE;

        for (Range3D range : ranges) {
            min_x = Math.min(min_x, range.min().x());
            min_y = Math.min(min_y, range.min().y());
            min_z = Math.min(min_z, range.min().z());

            max_x = Math.max(max_x, range.max().x());
            max_y = Math.max(max_y, range.max().y());
            max_z = Math.max(max_z, range.max().z());
        }

        return Range3D.of(Point3D.of(min_x, min_y, min_z), Point3D.of(max_x, max_y, max_z));
    }

    public static Range3D of(Point3D from, Point3D to) {
        return new Range3D(from, to);
    }

    public static long point3DCount(Range3D range) {
        return (long) Math.abs(1 + range.max.x() - range.min.x()) *
                Math.abs(1 + range.max.y() - range.min.y()) *
                Math.abs(1 + range.max.z() - range.min.z());
    }

    public boolean overlap(Range3D other) {
        return contains(other) ||
                (((min.x() >= other.min.x() && min.x() <= other.max.x()) && (min.y() >= other.min.y() && min.y() <= other.max.y()) && (min.z() >= other.min.z() && min.z() <= other.max.z())) ||
                        ((max.x() >= other.min.x() && max.x() <= other.max.x()) && (max.y() >= other.min.y() && max.y() <= other.max.y()) && (max.z() >= other.min.z() && max.z() <= other.max.z())));
    }

    public Optional<Range3D> carve(Range3D other) {
        if (!overlap(other))
            return Optional.empty();

        int min_x = Math.max(min.x(), other.min.x());
        int max_x = Math.min(max.x(), other.max.x());
        int min_y = Math.max(min.y(), other.min.y());
        int max_y = Math.min(max.y(), other.max.y());
        int min_z = Math.max(min.z(), other.min.z());
        int max_z = Math.min(max.z(), other.max.z());

        return Optional.of(Range3D.of(Point3D.of(min_x, min_y, min_z), Point3D.of(max_x, max_y, max_z)));
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
                IntStream.range(min.z(), max.z() + 1).mapToObj(z-> Point3D.of(x, y, z)).collect(Collectors.toSet())
        ).flatMap(Collection::stream).collect(Collectors.toSet()))
                .flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
