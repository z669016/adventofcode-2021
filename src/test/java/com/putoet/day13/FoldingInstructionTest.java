package com.putoet.day13;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoldingInstructionTest {

    @Test
    void of() {
        final List<FoldingInstruction> instructions =
                FoldingInstruction.of(ResourceLines.list("/day13.txt"));

        assertEquals(2, instructions.size());
//        System.out.println(instructions);
    }
}