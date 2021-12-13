package com.putoet.day13;

import com.putoet.grid.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransparentPaper {
    public static char EMPTY = '.';
    public static char DOT = '#';

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

    public long dots() {
        long count = 0;

        for (int y = 0; y < my; y++) {
            for (int x = 0; x <mx; x++) {
                if (grid[y][x] == DOT)
                    count++;
            }
        }

        return count;
    }

    public TransparentPaper foldLeft(int foldLine) {
        assert foldLine > 0;
        assert foldLine < my;

        final int rightX = mx - foldLine - 1;
        int newMx = Math.max(foldLine, rightX);

        final char[][] newGrid = new char[my][newMx];
        for (char[] row : newGrid) {
            Arrays.fill(row, EMPTY);
        }

        foldLeftCopyLeft(grid, newGrid, foldLine, newMx - foldLine);
        foldLeftCopyRight(grid, newGrid, foldLine + 1);

        return new TransparentPaper(newGrid);
    }

    private void foldLeftCopyLeft(char[][] grid, char[][] newGrid, int until, int offset) {
        for (int x = 0; x < until; x++) {
            for (int y = 0; y < my; y++) {
                if (newGrid[y][x + offset] == EMPTY)
                    newGrid[y][x + offset] = grid[y][x];
            }
        }
    }

    private void foldLeftCopyRight(char[][] grid, char[][] newGrid, int from) {
        for (int x = 0; x < mx - from; x++) {
            for (int y = 0; y < my; y++) {
                if (newGrid[y][newGrid[y].length - 1 - x] == EMPTY)
                    newGrid[y][newGrid[y].length - 1 - x] = grid[y][x + from];
            }
        }
    }


    public TransparentPaper foldUp(int foldLine) {
        assert foldLine > 0;
        assert foldLine < my;

        final int bottomY = my - foldLine - 1;
        int newMy = Math.max(foldLine, bottomY);

        final char[][] newGrid = new char[newMy][mx];
        for (char[] row : newGrid) {
            Arrays.fill(row, EMPTY);
        }

        foldUpCopyTop(grid, newGrid, foldLine, newMy - foldLine);
        foldUpCopyBottom(grid, newGrid, foldLine + 1);

        return new TransparentPaper(newGrid);
    }

    private void foldUpCopyTop(char[][] grid, char[][] newGrid, int until, int offset) {
        for (int y = 0; y < until; y++) {
            for (int x = 0; x < mx; x++) {
                if (newGrid[y + offset][x] == EMPTY)
                    newGrid[y + offset][x] = grid[y][x];
            }
        }
    }

    private void foldUpCopyBottom(char[][] grid, char[][] newGrid, int from) {
        for (int y = 0; y < my - from; y++) {
            for (int x = 0; x < mx; x++) {
                if (newGrid[newGrid.length - 1 - y][x] == EMPTY)
                    newGrid[newGrid.length - 1 - y][x] = grid[y + from][x];
            }
        }
    }

    public static TransparentPaper of(List<String> input) {
        assert input != null;
        assert !input.isEmpty();

        final List<Point> dots = new ArrayList<>();

        int mx = Integer.MIN_VALUE;
        int my = Integer.MIN_VALUE;
        int offset = 0;
        String line = input.get(offset);
        while (line.trim().length() > 0) {
            final Point dot = asPoint(line);
            mx = Math.max(mx, dot.x());
            my = Math.max(my, dot.y());
            dots.add(dot);
            line = input.get(++offset);
        }
        mx++;
        my++;

        final char[][] grid = new char[my][mx];
        for (char[] row : grid) {
            Arrays.fill(row, EMPTY);
        }

        dots.forEach(dot -> grid[dot.y()][dot.x()] = DOT);

        return new TransparentPaper(grid);
    }

    public static Point asPoint(String line) {
        assert line != null;
        final String[] split = line.trim().split(",");
        return Point.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            sb.append(String.valueOf(row));
            sb.append('\n');
        }
        return sb.toString();
    }
}
