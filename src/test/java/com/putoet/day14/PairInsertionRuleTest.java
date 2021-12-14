package com.putoet.day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairInsertionRuleTest {

    @Test
    void of() {
        assertThrows(AssertionError.class, () -> PairInsertionRule.of(null));
        assertThrows(IllegalArgumentException.class, () -> PairInsertionRule.of("A - B"));
        assertThrows(IllegalArgumentException.class, () -> PairInsertionRule.of("A -> "));
        assertThrows(IllegalArgumentException.class, () -> PairInsertionRule.of(" -> B"));
        assertThrows(IllegalArgumentException.class, () -> PairInsertionRule.of("A -> B"));
        assertThrows(IllegalArgumentException.class, () -> PairInsertionRule.of("AA -> BB"));

        final PairInsertionRule rule = PairInsertionRule.of("AA -> B");
        assertEquals("AA", rule.from());
        assertEquals("B", rule.insert());
    }
}