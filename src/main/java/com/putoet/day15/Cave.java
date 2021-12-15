package com.putoet.day15;

import com.putoet.grid.Point;

import java.util.List;

public record Cave(char[][] riskLevels) {

    public static Cave of(List<String> map) {
        assert map != null;

        final char[][] riskLevels = new char[map.size()][];
        for (int i = 0; i < map.size(); i++) {
            riskLevels[i] = map.get(i).toCharArray();
        }
        return new Cave(riskLevels);
    }

    private static char increaseRiskLevel(char riskLevel, int increase) {
        riskLevel += increase;

        return (char) (riskLevel > '9' ? riskLevel - 9 : riskLevel);
    }

    public Cave expand(int times) {
        assert times > 0 && times < 9;

        final int height = this.riskLevels.length;
        final int width = this.riskLevels[0].length;
        final char[][] riskLevels = new char[height * times][width * times];


        for (int row = 0; row < times; row++) {
            for (int y = 0; y < this.riskLevels.length; y++) {
                final int newY = height * row + y;
                for (int x = 0; x < this.riskLevels[y].length; x++)
                    riskLevels[newY][x] = increaseRiskLevel(this.riskLevels[y][x], row);
            }

            for (int column = 1; column < times; column++) {
                for (int y = 0; y < height; y++) {
                    final int newY = height * row + y;
                    for (int x = 0; x < width; x++) {
                        final int newX = width * column + x;
                        riskLevels[newY][newX] = increaseRiskLevel(riskLevels[newY][x], column);
                    }
                }
            }
        }

        return new Cave(riskLevels);
    }

    public Point start() {
        return Point.ORIGIN;
    }

    public Point end() {
        final int y = riskLevels.length - 1;
        final int x = riskLevels[y].length - 1;

        return Point.of(x, y);
    }

    public int riskLevel(Point point) {
        if (!contains(point))
            throw new IllegalArgumentException("Coordinates not in this cave " + point);

        return riskLevels[point.y()][point.x()] - '0';
    }

    public boolean contains(Point point) {
        assert point != null;

        return point.y() >= 0 && point.y() < riskLevels.length
                && point.x() >= 0 && point.x() < riskLevels[0].length;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (char[] row : riskLevels) {
            sb.append(String.valueOf(row));
            sb.append('\n');
        }
        return sb.toString();
    }
}
