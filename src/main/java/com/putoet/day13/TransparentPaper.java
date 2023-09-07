package com.putoet.day13;

import com.putoet.grid.Point;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TransparentPaper {
    public static final char EMPTY = '.';
    public static final char DOT = '#';

    private final char[][] grid;
    private final int my;
    private final int mx;

    private TransparentPaper(char[][] grid) {
        assert grid != null;
        assert grid.length > 0;
        assert grid[0].length > 0;

        this.grid = grid;
        my = grid.length;
        mx = grid[0].length;
    }

    public static TransparentPaper of(@NotNull List<String> input) {
        assert !input.isEmpty();

        final List<Point> dots = new ArrayList<>();
        final Pair<Integer,Integer> mxmy = findDots(dots, input);

        final char[][] grid = emptyGrid(mxmy.getValue0(), mxmy.getValue1());
        dots.forEach(dot -> grid[dot.y()][dot.x()] = DOT);

        return new TransparentPaper(grid);
    }

    private static Pair<Integer,Integer> findDots(List<Point> dots, List<String> input) {
        var mx = Integer.MIN_VALUE;
        var my = Integer.MIN_VALUE;
        var offset = 0;

        var line = input.get(offset);
        while (!line.trim().isEmpty()) {
            final Point dot = asPoint(line);
            mx = Math.max(mx, dot.x());
            my = Math.max(my, dot.y());
            dots.add(dot);
            line = input.get(++offset);
        }

        mx++;
        my++;

        return Pair.with(mx, my);
    }

    private static char[][] emptyGrid(int mx, int my) {
        final var grid = new char[my][mx];
        for (var row : grid) {
            Arrays.fill(row, EMPTY);
        }
        return grid;
    }

    public static Point asPoint(@NotNull String line) {
        final var split = line.trim().split(",");
        return Point.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public long dots() {
        var count = 0L;

        for (var y = 0; y < my; y++) {
            for (var x = 0; x < mx; x++) {
                if (grid[y][x] == DOT)
                    count++;
            }
        }

        return count;
    }

    public TransparentPaper fold(@NotNull List<FoldingInstruction> instructions) {
        var paper = this;
        for (var instruction : instructions) {
            paper = paper.fold(instruction);
        }

        return paper;
    }

    public TransparentPaper fold(@NotNull FoldingInstruction instruction) {
        return switch (instruction.along()) {
            case X -> foldLeft(instruction.offset());
            case Y -> foldUp(instruction.offset());
        };
    }

    public TransparentPaper foldLeft(int foldLine) {
        assert foldLine > 0;
        assert foldLine < my;

        final var rightX = mx - foldLine - 1;
        var newMx = Math.max(foldLine, rightX);

        final var newGrid = emptyGrid(newMx, my);

        foldLeftCopyLeft(grid, newGrid, foldLine, newMx - foldLine);
        foldLeftCopyRight(grid, newGrid, foldLine + 1);

        return new TransparentPaper(newGrid);
    }

    private void foldLeftCopyLeft(char[][] grid, char[][] newGrid, int until, int offset) {
        for (var x = 0; x < until; x++) {
            for (var y = 0; y < my; y++) {
                if (newGrid[y][x + offset] == EMPTY)
                    newGrid[y][x + offset] = grid[y][x];
            }
        }
    }

    private void foldLeftCopyRight(char[][] grid, char[][] newGrid, int from) {
        for (var x = 0; x < mx - from; x++) {
            for (var y = 0; y < my; y++) {
                if (newGrid[y][newGrid[y].length - 1 - x] == EMPTY)
                    newGrid[y][newGrid[y].length - 1 - x] = grid[y][x + from];
            }
        }
    }

    public TransparentPaper foldUp(int foldLine) {
        assert foldLine > 0;
        assert foldLine < my;

        final var bottomY = my - foldLine - 1;
        var newMy = Math.max(foldLine, bottomY);

        final var newGrid = emptyGrid(mx, newMy);

        foldUpCopyTop(grid, newGrid, foldLine, newMy - foldLine);
        foldUpCopyBottom(grid, newGrid, foldLine + 1);

        return new TransparentPaper(newGrid);
    }

    private void foldUpCopyTop(char[][] grid, char[][] newGrid, int until, int offset) {
        for (var y = 0; y < until; y++) {
            for (var x = 0; x < mx; x++) {
                if (newGrid[y + offset][x] == EMPTY)
                    newGrid[y + offset][x] = grid[y][x];
            }
        }
    }

    private void foldUpCopyBottom(char[][] grid, char[][] newGrid, int from) {
        for (var y = 0; y < my - from; y++) {
            for (var x = 0; x < mx; x++) {
                if (newGrid[newGrid.length - 1 - y][x] == EMPTY)
                    newGrid[newGrid.length - 1 - y][x] = grid[y + from][x];
            }
        }
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var row : grid) {
            sb.append(String.valueOf(row));
            sb.append('\n');
        }
        return sb.toString();
    }
}
