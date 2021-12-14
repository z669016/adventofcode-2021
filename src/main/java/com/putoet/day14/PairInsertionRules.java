package com.putoet.day14;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public record PairInsertionRules(String template, Map<String, PairInsertionRule> rules) {
    public static PairInsertionRules of(List<String> input) {
        assert input != null;
        assert input.size() > 2;

        final Iterator<String> iter = input.iterator();
        final String template = iter.next();
        iter.next();

        final List<PairInsertionRule> rules = new ArrayList<>();
        while (iter.hasNext())
            rules.add(PairInsertionRule.of(iter.next()));

        return new PairInsertionRules(template, rules.stream().collect(Collectors.toMap(PairInsertionRule::from, rule -> rule)));
    }

    public Pair<String, Map<String, Long>> transform(int count) {
        assert count >= 0;

        Map<String, Long> transformed = combinationMap(template);
        String lastElement = template.substring(template.length() - 2);
        while (count > 0) {
            final Map<String, Long> next = new HashMap<>();
            for (Map.Entry<String, Long> entry : transformed.entrySet()) {
                final PairInsertionRule rule = rules.get(entry.getKey());
                final String first = rule.first();
                final String second = rule.second();
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

    public Pair<String, Long> mostCommonElement(Pair<String, Map<String, Long>> transformed) {
        assert transformed.getValue0() != null;
        assert transformed.getValue1() != null;
        assert transformed.getValue1().size() > 0;

        final Map<String, Long> elementMap = elementMap(transformed);
        return elementMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> Pair.with(e.getKey(), e.getValue()))
                .get();
    }

    public Pair<String, Long> leastCommonElement(Pair<String, Map<String, Long>> transformed) {
        assert transformed.getValue0() != null;
        assert transformed.getValue1() != null;
        assert transformed.getValue1().size() > 0;

        final Map<String, Long> elementMap = elementMap(transformed);
        return elementMap.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(e -> Pair.with(e.getKey(), e.getValue()))
                .get();
    }

    private Map<String, Long> combinationMap(String template) {
        final Map<String, Long> elementMap = new HashMap<>();

        for (int i = 0; i < template.length() - 1; i++) {
            final String key = template.substring(i, i + 2);
            elementMap.put(key, elementMap.getOrDefault(key, 0L) + 1);
        }
        return elementMap;
    }

    private Map<String, Long> elementMap(Pair<String, Map<String, Long>> transformed) {
        final String lastElement = transformed.getValue0();
        final Map<String, Long> combinationMap = transformed.getValue1();

        final Map<String, Long> elementMap = new HashMap<>();
        combinationMap.forEach((key, value) -> {
            final String element = key.substring(0, 1);
            elementMap.put(element, elementMap.getOrDefault(element, 0L) + value);
        });
        elementMap.put(lastElement.substring(1), elementMap.getOrDefault(lastElement.substring(1), 0L) + 1);
        return elementMap;
    }

}
