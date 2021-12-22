package com.putoet.day22;

import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    private static final List<String> lines = ResourceLines.list("/day22.txt");
    private static final List<Command> commands = Command.of(lines);

    @Test
    void range() {
        final Range3D expected = new Range3D(Point3D.of(-54112, -85059, -27449), Point3D.of(23432, 81175, 53682));
        assertEquals(expected, Command.range(commands));
    }
}