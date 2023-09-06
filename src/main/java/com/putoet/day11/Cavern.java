package com.putoet.day11;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

record Cavern(Octopus[][] octopuses) {

    public static Cavern of(@NotNull List<String> lines) {
        assert !lines.isEmpty();

        final var octopuses = new Octopus[lines.size()][lines.get(0).length()];
        for (var y = 0; y < lines.size(); y++) {
            final var line = lines.get(y);
            for (var x = 0; x < line.length(); x++) {
                assert Character.isDigit(line.charAt(x));
                final var energyLevel = line.charAt(x) - '0';
                octopuses[y][x] = new Octopus(Point.of(x, y), energyLevel);
            }
        }

        wire(octopuses);

        return new Cavern(octopuses);
    }

    private static void wire(Octopus[][] octopuses) {
        for (var y = 0; y < octopuses.length; y++) {
            final var max = octopuses[y].length;

            for (var x = 0; x < max; x++) {
                if (y > 0) {
                    if (x > 0) octopuses[y][x].addNeighbour(octopuses[y - 1][x - 1]);
                    octopuses[y][x].addNeighbour(octopuses[y - 1][x]);
                    if (x + 1 < max) octopuses[y][x].addNeighbour(octopuses[y - 1][x + 1]);
                }

                if (x > 0) octopuses[y][x].addNeighbour(octopuses[y][x - 1]);
                if (x + 1 < max) octopuses[y][x].addNeighbour(octopuses[y][x + 1]);

                if (y + 1 < octopuses.length) {
                    if (x > 0) octopuses[y][x].addNeighbour(octopuses[y + 1][x - 1]);
                    octopuses[y][x].addNeighbour(octopuses[y + 1][x]);
                    if (x + 1 < max) octopuses[y][x].addNeighbour(octopuses[y + 1][x + 1]);
                }
            }
        }
    }

    public int step() {
        Arrays.stream(octopuses).flatMap(Arrays::stream).forEach(Octopus::raise);
        var flashed = false;
        var count = 0;

        do {
            flashed = false;
            for (var octopus : octopuses)
                for (var value : octopus) {
                    if (value.flash()) {
                        count++;
                        flashed = true;
                    }
                }
        } while (flashed);

        return count;
    }

    public int size() {
        return Arrays.stream(octopuses).mapToInt(line -> line.length).sum();
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var line : octopuses) {
            for (Octopus octopus : line) {
                sb.append(octopus.energyLevel());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
