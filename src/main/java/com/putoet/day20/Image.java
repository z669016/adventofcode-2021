package com.putoet.day20;

import com.putoet.grid.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Image(char[][] grid) {
    public static final char LIT = '#';
    public static final char DARK = '.';

    public Image {
        Objects.requireNonNull(grid);
    }

    public static Image of(List<String> lines) {
        assert lines != null;

        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size();i++)
            grid[i] = lines.get(i).toCharArray();

        return new Image(grid);
    }

    public static Image of(String line) {
        return of(Arrays.stream(line.split("\n")).toList());
    }

    public int pixelsLit() {
        int count = 0;
        for (char[] row : grid) {
            for (char c : row)
                if (c == LIT)
                    count++;
        }
        return count;
    }

    public Point size() {
        return Point.of(grid[0].length, grid.length);
    }

    @Override
    public String toString() {
        return Arrays.stream(grid).map(String::valueOf).collect(Collectors.joining("\n"));
    }
}
