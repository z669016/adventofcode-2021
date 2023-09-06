package com.putoet.day14;

import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairInsertionRulesTest {
    private final PairInsertionRules insertionRules = PairInsertionRules.of(ResourceLines.list("/day14.txt"));

    @Test
    void commonElement() {
        var transformed = insertionRules.transform(10);
        assertEquals(Pair.with("B", 1749L), insertionRules.mostCommonElement(transformed));
        assertEquals(Pair.with("H", 161L), insertionRules.leastCommonElement(transformed));

        transformed = insertionRules.transform(40);
        assertEquals(Pair.with("B", 2192039569602L), insertionRules.mostCommonElement(transformed));
        assertEquals(Pair.with("H", 3849876073L), insertionRules.leastCommonElement(transformed));
    }
}