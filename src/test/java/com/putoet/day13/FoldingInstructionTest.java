package com.putoet.day13;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoldingInstructionTest {

    @Test
    void of() {
        final var instructions = FoldingInstruction.of(ResourceLines.list("/day13.txt"));
        assertEquals(2, instructions.size());
    }
}