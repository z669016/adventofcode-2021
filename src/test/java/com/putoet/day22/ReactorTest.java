package com.putoet.day22;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactorTest {

    @Test
    void processLimited() {
        final List<Command> commands = Command.of(ResourceLines.list("/day22.txt"));
        assertEquals(590784, Reactor.processLimited(commands));
    }

    @Test
    void process() {
        final List<Command> commands = Command.of(ResourceLines.list("/day22-2.txt"));
        assertEquals(2758514936282235L, Reactor.process(commands));
    }
}