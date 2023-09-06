package com.putoet.day14;

import org.jetbrains.annotations.NotNull;

record PairInsertionRule(@NotNull String from, @NotNull String insert) {
    public static PairInsertionRule of(@NotNull String rule) {
        final var split = rule.split(" -> ");
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
