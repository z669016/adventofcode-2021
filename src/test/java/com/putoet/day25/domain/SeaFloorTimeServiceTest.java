package com.putoet.day25.domain;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaFloorTimeServiceTest {
    private static final String STEP0 = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>""";

    private static final String STEP1 = """
            ....>.>v.>
            v.v>.>v.v.
            >v>>..>v..
            >>v>v>.>.v
            .>v.v...v.
            v>>.>vvv..
            ..v...>>..
            vv...>>vv.
            >.v.v..v.v""";

    private static final String STEP2 = """
            >.v.v>>..v
            v.v.>>vv..
            >v>.>.>.v.
            >>v>v.>v>.
            .>..v....v
            .>v>>.v.v.
            v....v>v>.
            .vv..>>v..
            v>.....vv.""";

    @Test
    void advance() {
        final SeaFloor start = SeaFloorFactoryPolicy.from(STEP0);

        final Pair<SeaFloor,Integer> afterStep1 = SeaFloorTimeService.advance(start, 1);
        assertEquals(STEP1, afterStep1.getValue0().toString() );
        assertEquals(24, afterStep1.getValue1());

        final Pair<SeaFloor,Integer> afterStep2 = SeaFloorTimeService.advance(start, 2);
        assertEquals(STEP2, afterStep2.getValue0().toString());
        assertEquals(24, afterStep2.getValue1());
    }
}