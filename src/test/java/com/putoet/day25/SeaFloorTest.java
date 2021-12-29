package com.putoet.day25;

import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;
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
    private final String EXPECTED = """
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
    private final String STEP1 = """
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

    @Test
    void of() {
        assertEquals(EXPECTED, SeaFloor.of(ResourceLines.list("/day25.txt")).toString());
    }

    @Test
    void step() {
        SeaFloor seaFloor = SeaFloor.of(ResourceLines.list("/day25.txt"));
        int step = 1;
        Pair<SeaFloor, Integer> next = seaFloor.step();
        while (next.getValue1() != 0) {
            switch (step) {
                case 1 -> assertEquals(STEP1, next.getValue0().toString());
                case 2 -> assertEquals(STEP2, next.getValue0().toString());
            }
            step++;
            next = next.getValue0().step();
        }

        assertEquals(58, step);
        System.out.println(next.getValue0().toString());
    }
}