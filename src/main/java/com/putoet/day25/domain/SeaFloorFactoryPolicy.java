package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Id;
import com.putoet.hex.domain.Policy;
import com.putoet.hex.domain.PolicyViolation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SeaFloorFactoryPolicy implements Policy<List<String>, SeaFloor> {
    public static final String VIOLATION = "Cannot convert \"%s\" into a valid sea floor.";

    private static final char EMPTY = '.';

    private SeaFloorFactoryPolicy() {
    }

    public static SeaFloorFactoryPolicy getInstance() {
        return LoadSingleton.INSTANCE;
    }

    public static SeaFloor from(String map) {
        if (map == null)
            throw new PolicyViolation(String.format(VIOLATION, null));

        return getInstance().apply(Arrays.asList(map.split("\n")));
    }

    public static SeaFloor from(List<String> map) {
        return getInstance().apply(map);
    }

    @Override
    public SeaFloor apply(List<String> from) {
        if (from == null)
            throw new PolicyViolation(String.format("Cannot convert \"%s\" into a valid sea floor.", from));

        try {
            int maxY = 0, maxX = 0;
            final List<SeaCucumber> cucumbers = new ArrayList<>();
            for (int y = 0; y < from.size(); y++) {
                maxY = Math.max(y, maxY);

                final String row = from.get(y);
                for (int x = 0; x < row.length(); x++) {
                    maxX = Math.max(x, maxX);
                    if (EMPTY != row.charAt(x)) {
                        final String symbol = String.valueOf(row.charAt(x));
                        cucumbers.add(SeaCucumberFactoryPolicy.from(symbol, Point.of(x, y)));
                    }
                }
            }

            return new SeaFloor(Id.generatedId(), Point.of(maxX + 1, maxY + 1), Collections.unmodifiableList(cucumbers));
        } catch (RuntimeException cause) {
            throw new PolicyViolation(String.format(VIOLATION, from), cause);
        }
    }

    private static class LoadSingleton {
        static final SeaFloorFactoryPolicy INSTANCE = new SeaFloorFactoryPolicy();
    }
}
