package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

class BurrowTest {

    @Test
    void of() {
        final var lines = ResourceLines.list("/day23.txt");
        Burrow.of(lines);
    }
}