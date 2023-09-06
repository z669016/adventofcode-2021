package com.putoet.day14;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

record PairInsertionRules(@NotNull String template, @NotNull Map<String, PairInsertionRule> rules) {
    public static PairInsertionRules of(@NotNull List<String> input) {
        assert input.size() > 2;

        final var iter = input.iterator();
        final var template = iter.next();
        iter.next();

        final var rules = new ArrayList<PairInsertionRule>();
        while (iter.hasNext())
            rules.add(PairInsertionRule.of(iter.next()));

        return new PairInsertionRules(template, rules.stream().collect(Collectors.toMap(PairInsertionRule::from, rule -> rule)));
    }

    public Pair<String, Map<String, Long>> transform(int count) {
        assert count >= 0;

        var transformed = combinationMap(template);
        var lastElement = template.substring(template.length() - 2);
        while (count > 0) {
            final var next = new HashMap<String, Long>();
            for (var entry : transformed.entrySet()) {
                final var rule = rules.get(entry.getKey());
                final var first = rule.first();
                final var second = rule.second();
                next.put(first, next.getOrDefault(first, 0L) + entry.getValue());
                next.put(second, next.getOrDefault(second, 0L) + entry.getValue());

                if (entry.getKey().equals(lastElement))
                    lastElement = second;
            }

            transformed = next;
            count--;
        }

        return Pair.with(lastElement, transformed);
    }

    public Pair<String, Long> mostCommonElement(@NotNull Pair<String, Map<String, Long>> transformed) {
        assert transformed.getValue0() != null;
        assert transformed.getValue1() != null;
        assert !transformed.getValue1().isEmpty();

        final var elementMap = elementMap(transformed);
        return elementMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> Pair.with(e.getKey(), e.getValue()))
                .orElseThrow();
    }

    public Pair<String, Long> leastCommonElement(@NotNull Pair<String, Map<String, Long>> transformed) {
        assert transformed.getValue0() != null;
        assert transformed.getValue1() != null;
        assert !transformed.getValue1().isEmpty();

        final Map<String, Long> elementMap = elementMap(transformed);
        return elementMap.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(e -> Pair.with(e.getKey(), e.getValue()))
                .orElseThrow();
    }

    private Map<String, Long> combinationMap(String template) {
        final var elementMap = new HashMap<String , Long>();

        for (var i = 0; i < template.length() - 1; i++) {
            final var key = template.substring(i, i + 2);
            elementMap.put(key, elementMap.getOrDefault(key, 0L) + 1);
        }
        return elementMap;
    }

    private Map<String, Long> elementMap(Pair<String, Map<String, Long>> transformed) {
        final var lastElement = transformed.getValue0();
        final var combinationMap = transformed.getValue1();

        final var elementMap = new HashMap<String, Long>();
        combinationMap.forEach((key, value) -> {
            final var element = key.substring(0, 1);
            elementMap.put(element, elementMap.getOrDefault(element, 0L) + value);
        });
        elementMap.put(lastElement.substring(1), elementMap.getOrDefault(lastElement.substring(1), 0L) + 1);
        return elementMap;
    }

}
