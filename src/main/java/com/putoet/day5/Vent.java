package com.putoet.day5;

import com.putoet.grid.Point;

import java.util.List;
import java.util.stream.Collectors;

public record Vent(Point start, Point end) {
    public Vent {
        assert start != null;
        assert end != null;
    }

    public static List<Vent> of(List<String> lines) {
        assert lines != null;

        return lines.stream().map(Vent::of).collect(Collectors.toList());
    }

    public static Vent of(String line) {
        assert line != null;

        final String[] split = line.trim().split(" -> ");
        assert split.length == 2;

        return new Vent(point(split[0]), point(split[1]));
    }

    public static Point point(String line) {
        assert line != null;

        final String[] split = line.trim().split(",");
        assert split.length == 2;

        return Point.of(Integer.parseInt(split[0]), Integer.parseInt((split[1])));
    }
}
