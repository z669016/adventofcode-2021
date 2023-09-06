package com.putoet.day5;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record Vent(@NotNull Point start, @NotNull Point end) {
    public static List<Vent> of(@NotNull List<String> lines) {
        return lines.stream().map(Vent::of).toList();
    }

    public static Vent of(@NotNull String line) {
        final var split = line.trim().split(" -> ");
        assert split.length == 2;

        return new Vent(point(split[0]), point(split[1]));
    }

    public static Point point(@NotNull String line) {
        final var split = line.trim().split(",");
        assert split.length == 2;

        return Point.of(Integer.parseInt(split[0]), Integer.parseInt((split[1])));
    }
}
