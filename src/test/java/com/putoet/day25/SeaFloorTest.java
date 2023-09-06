package com.putoet.day25;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeaFloorTest {
    private static final String STEP2 = """
            >.v.v>>..v
            v.v.>>vv..
            >v>.>.>.v.
            >>v>v.>v>.
            .>..v....v
            .>v>>.v.v.
            v....v>v>.
            .vv..>>v..
            v>.....vv.
            """;

    @Test
    void of() {
        String EXPECTED = """
                v...>>.vv>
                .vv>>.vv..
                >>.>v>...v
                >>v>>.>.v.
                v>v.vv.v..
                >.>>..v...
                .vv..>.>v.
                v.v..>>v.v
                ....v..v.>
                """;

        assertEquals(EXPECTED, SeaFloor.of(ResourceLines.list("/day25.txt")).toString());
    }

    @Test
    void step() {
        final var seaFloor = SeaFloor.of(ResourceLines.list("/day25.txt"));

        var step = 1;
        var next = seaFloor.step();
        while (next.getValue1() != 0) {
            String STEP1 = """
                    ....>.>v.>
                    v.v>.>v.v.
                    >v>>..>v..
                    >>v>v>.>.v
                    .>v.v...v.
                    v>>.>vvv..
                    ..v...>>..
                    vv...>>vv.
                    >.v.v..v.v
                    """;

            switch (step) {
                case 1 -> assertEquals(STEP1, next.getValue0().toString());
                case 2 -> assertEquals(STEP2, next.getValue0().toString());
            }
            step++;
            next = next.getValue0().step();
        }

        assertEquals(58, step);
    }
}