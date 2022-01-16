package com.putoet.day25.framework;

import com.putoet.day25.application.Day25OutputPort;
import com.putoet.day25.domain.SeaFloor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringAdapterTest {
    private static final String INPUT = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>""";

    @Test
    void input() {
        final Day25OutputPort outputPort = new StringAdapter(INPUT);
        final SeaFloor seaFloor = outputPort.input();

        assertEquals(INPUT, seaFloor.toString());
    }
}