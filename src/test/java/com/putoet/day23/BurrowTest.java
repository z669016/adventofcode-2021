package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BurrowTest {

    @Test
    void of() {
        final List<String> lines = ResourceLines.list("/day23.txt");
        final Burrow burrow = Burrow.of(lines);
    }
}