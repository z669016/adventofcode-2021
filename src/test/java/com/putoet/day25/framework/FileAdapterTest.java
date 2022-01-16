package com.putoet.day25.framework;

import com.putoet.day25.application.Day25OutputPort;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAdapterTest {

    @Test
    void input() {
        final Day25OutputPort outputPort = new FileAdapter("/day25.txt");
        final var seaFloor = outputPort.input();

        assertEquals(String.join("\n", ResourceLines.list("/day25.txt")), seaFloor.toString());
    }
}