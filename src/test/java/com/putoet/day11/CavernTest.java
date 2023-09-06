package com.putoet.day11;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CavernTest {
    private Cavern cavern;

    @BeforeEach
    void setup() {
        cavern = Cavern.of(List.of(
                "11111",
                "19991",
                "19191",
                "19991",
                "11111"
        ));
    }

    @Test
    void of() {
        assertEquals("11111\n19991\n19191\n19991\n11111\n", cavern.toString());
    }

    @Test
    void step() {
        var count = cavern.step();
        assertEquals("34543\n40004\n50005\n40004\n34543\n", cavern.toString());
        assertEquals(9, count);

        count = cavern.step();
        assertEquals("45654\n51115\n61116\n51115\n45654\n", cavern.toString());
        assertEquals(0, count);
    }

    @Test
    void steps() {
        final var input = ResourceLines.list("/day11.txt");
        final var cavern = Cavern.of(input);

        var count = 0;
        for (var i = 0; i < 10; i++)
            count += cavern.step();

        assertEquals(204, count);

        for (var i = 10; i < 100; i++)
            count += cavern.step();

        assertEquals(1656, count);
    }

    @Test
    void size() {
        assertEquals(25, cavern.size());
    }

    @Test
    void allFlash() {
        final var input = ResourceLines.list("/day11.txt");
        final var cavern = Cavern.of(input);

        int count = 1;
        while (cavern.step() != cavern.size())
            count++;

        assertEquals(195, count);
    }
}