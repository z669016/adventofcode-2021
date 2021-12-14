package com.putoet.day14;

public record PairInsertionRule(String from, String insert) {
    public static PairInsertionRule of(String rule) {
        assert rule != null;

        final String[] split = rule.split(" -> ");
        if (split.length != 2 || split[0].length() != 2 || split[1].length() != 1)
            throw new IllegalArgumentException("Invalid insertion rule " + rule);

        return new PairInsertionRule(split[0], split[1]);
    }

    public String first() {
        return String.valueOf(new char[]{from.charAt(0), insert.charAt(0)});
    }

    public String second() {
        return String.valueOf(new char[]{insert.charAt(0), from.charAt(1)});
    }
}
