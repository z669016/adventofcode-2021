package com.putoet.day20;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

record ImageEnhancer(char[] algorithm) {

    public ImageEnhancer {
        Objects.requireNonNull(algorithm);
        assert algorithm.length == 512;
    }

    public static int index(char[] index) {
        var count = 0;
        for (var i = 0; i < 9; i++) {
            count = count << 1 | (index[i] == Image.LIT ? 1 : 0);
        }
        return count;
    }

    public static char[][] enlarged(char[][] grid) {
        final var enlarged = new char[grid.length + 2][grid[0].length + 2];
        for (var row : enlarged) {
            Arrays.fill(row, Image.DARK);
        }
        return enlarged;
    }

    public Image enhance(@NotNull Image image) {
        return enhance(image, 1);
    }

    public Image enhance(@NotNull Image image, int times) {
        assert times >= 0;

        if (times == 0)
            return image;

        var grid = image.grid();
        for (var turn = 0; turn < times; turn++) {
            final var height = grid.length;
            final var width = grid[0].length;

            final var enlarged = enlarged(grid);
            for (var y = -1; y < height + 1; y++) {
                for (var x = -1; x < width + 1; x++) {
                    enlarged[y + 1][x + 1] = valueFor(fromGrid(grid, x, y, turn));
                }
            }
            grid = enlarged;
        }

        return new Image(grid);
    }

    private char valueFor(int index) {
        return algorithm[index];
    }

    public int fromGrid(char[][] grid, int x, int y, int turn) {
        assert grid != null;

        return index(new char[]{
                charAt(grid, x - 1, y - 1, turn),
                charAt(grid, x, y - 1, turn),
                charAt(grid, x + 1, y - 1, turn),
                charAt(grid, x - 1, y, turn),
                charAt(grid, x, y, turn),
                charAt(grid, x + 1, y, turn),
                charAt(grid, x - 1, y + 1, turn),
                charAt(grid, x, y + 1, turn),
                charAt(grid, x + 1, y + 1, turn)
        });
    }

    private char charAt(char[][] grid, int x, int y, int turn) {
        var infinite = Image.DARK;
        if (algorithm[0] == Image.LIT && algorithm[algorithm.length - 1] == Image.DARK)
            infinite = (turn % 2 == 0 ? Image.DARK : Image.LIT);

        return (y < 0 || y >= grid.length || x < 0 || x >= grid[y].length) ? infinite : grid[y][x];
    }
}
