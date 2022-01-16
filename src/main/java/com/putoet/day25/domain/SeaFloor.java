package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Entity;
import com.putoet.hex.domain.Id;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record SeaFloor(Id id, Point size, List<SeaCucumber> cucumbers) implements Entity {
    public static final char EMPTY = '.';

    public SeaFloor {
        final var sizeSpec = new SeaCucumberWithinSizeSpec(size);
        cucumbers.forEach(sizeSpec::enforce);
    }

    @Override
    public String toString() {
        final char[][] grid = new char[size.y()][size.x()];
        for (char[] row : grid) {
            Arrays.fill(row, EMPTY);
        }

        cucumbers.forEach(cucumber -> grid[cucumber.location().y()][cucumber.location().x()] = cucumber.symbol());
        return Arrays.stream(grid).map(String::valueOf).collect(Collectors.joining("\n"));
    }
}
