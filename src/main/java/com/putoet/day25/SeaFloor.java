package com.putoet.day25;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

record SeaFloor(char[][] grid) {
    public static final char EAST = '>';
    public static final char SOUTH = 'v';
    public static final char EMPTY = '.';

    public static SeaFloor of(@NotNull List<String> lines) {
        assert !lines.isEmpty();

        final var grid = new char[lines.size()][];
        for (var i = 0; i < lines.size(); i++)
            grid[i] = lines.get(i).toCharArray();

        return new SeaFloor(grid);
    }

    public Pair<SeaFloor, Integer> step() {
        final var copyOne = copy();

        var moved = 0;
        for (var y = 0; y < grid.length; y++)
            for (var x = 0; x < grid[y].length; x++)
                if (grid[y][x] == EAST && isEmpty(x + 1, y))
                    moved += copyOne.move(x, y, EAST);

        final var copyTwo = copyOne.copy();
        for (var y = 0; y < copyOne.grid.length; y++)
            for (var x = 0; x < copyOne.grid[y].length; x++)
                if (copyOne.grid[y][x] == SOUTH && copyOne.isEmpty(x,y + 1))
                    moved += copyTwo.move(x, y, SOUTH);

        return Pair.with(copyTwo, moved);
    }

    public boolean isEmpty(int x, int y) {
        if (y == grid.length)
            y = 0;

        if (x == grid[y].length)
            x = 0;

        return grid[y][x] == EMPTY;
    }

    private int move(int x, int y, char toMove) {
        if (grid[y][x] != toMove)
            return 0;

        if (grid[y][x] == EMPTY)
            return 0;

        if (grid[y][x] == EAST) {
            var nx = (x == grid[y].length - 1 ? 0 : x + 1);
            if (isEmpty(nx, y)) {
                grid[y][x] = EMPTY;
                grid[y][nx] = EAST;
                return 1;
            }
        }

        // SOUTH
        var ny = (y == grid.length - 1 ? 0 : y + 1);
        if (isEmpty(x, ny)) {
            grid[y][x] = EMPTY;
            grid[ny][x] = SOUTH;
            return 1;
        }

        return 0;
    }

    private SeaFloor copy() {
        final var copy = new char[grid.length][];
        for (var i = 0; i < grid.length; i++)
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);

        return new SeaFloor(copy);
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var row : grid)
            sb.append(String.valueOf(row)).append('\n');

        return sb.toString();
    }
}
