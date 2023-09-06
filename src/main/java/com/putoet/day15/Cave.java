package com.putoet.day15;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record Cave(char[][] riskLevels) {

    public static Cave of(@NotNull List<String> map) {
        final var riskLevels = new char[map.size()][];
        for (var i = 0; i < map.size(); i++) {
            riskLevels[i] = map.get(i).toCharArray();
        }
        return new Cave(riskLevels);
    }

    private static char increaseRiskLevel(char riskLevel, int increase) {
        riskLevel += (char) increase;

        return (char) (riskLevel > '9' ? riskLevel - 9 : riskLevel);
    }

    public Cave expand(int times) {
        assert times > 0 && times < 9;

        final var height = this.riskLevels.length;
        final var width = this.riskLevels[0].length;
        final var riskLevels = new char[height * times][width * times];


        for (var row = 0; row < times; row++) {
            for (var y = 0; y < this.riskLevels.length; y++) {
                final var newY = height * row + y;
                for (var x = 0; x < this.riskLevels[y].length; x++)
                    riskLevels[newY][x] = increaseRiskLevel(this.riskLevels[y][x], row);
            }

            for (var column = 1; column < times; column++) {
                for (var y = 0; y < height; y++) {
                    final var newY = height * row + y;
                    for (var x = 0; x < width; x++) {
                        final var newX = width * column + x;
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
        final var y = riskLevels.length - 1;
        final var x = riskLevels[y].length - 1;

        return Point.of(x, y);
    }

    public int riskLevel(@NotNull Point point) {
        if (!contains(point))
            throw new IllegalArgumentException("Coordinates not in this cave " + point);

        return riskLevels[point.y()][point.x()] - '0';
    }

    public boolean contains(@NotNull Point point) {
        return point.y() >= 0 && point.y() < riskLevels.length && point.x() >= 0 && point.x() < riskLevels[0].length;
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var row : riskLevels) {
            sb.append(String.valueOf(row));
            sb.append('\n');
        }
        return sb.toString();
    }
}
