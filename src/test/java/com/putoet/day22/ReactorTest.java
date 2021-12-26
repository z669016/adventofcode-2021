package com.putoet.day22;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReactorTest {

    @Test
    void size() {
        final List<Command> commands = Command.of(ResourceLines.list("/day22.txt"));
        final Reactor reactor = new Reactor();
        reactor.process(commands);

        assertEquals(590784, reactor.size());
    }
}