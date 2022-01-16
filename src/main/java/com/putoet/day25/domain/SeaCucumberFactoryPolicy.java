package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Id;
import com.putoet.hex.domain.Policy;
import com.putoet.hex.domain.PolicyViolation;
import org.javatuples.Pair;

public class SeaCucumberFactoryPolicy implements Policy<Pair<String, Point>, SeaCucumber> {
    public static final String VIOLATION = "Cannot convert \"%s\" into a valid sea cucumber.";

    private SeaCucumberFactoryPolicy() {
    }

    public static SeaCucumberFactoryPolicy getInstance() {
        return LoadSingleton.INSTANCE;
    }

    public static SeaCucumber from(String type, Point location) {
        return getInstance().apply(Pair.with(type, location));
    }

    @Override
    public SeaCucumber apply(Pair<String, Point> from) {
        if (from == null || from.getValue0() == null || from.getValue1() == null)
            throw new PolicyViolation(String.format(VIOLATION, from));

        try {
            return new SeaCucumber(Id.generatedId(), SeaCucumberTypeFactoryPolicy.from(from.getValue0()), from.getValue1());
        } catch (RuntimeException cause) {
            throw new PolicyViolation(String.format(VIOLATION, from), cause);
        }
    }

    private static class LoadSingleton {
        static final SeaCucumberFactoryPolicy INSTANCE = new SeaCucumberFactoryPolicy();
    }
}
