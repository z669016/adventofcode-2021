package com.putoet.day22;

import com.putoet.grid.Point3D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reactor {
    private final Set<Point3D> on = new HashSet<>();
    private final Range3D limit = Range3D.of(Point3D.of(-50, -50, -50), Point3D.of(50, 50, 50));

    public void process(List<Command> commands) {
        commands.forEach(this::process);
    }

    public void process(Command command) {
        if (limit.contains(command.range())) {
            if (command.on())
                on.addAll(command.range().toSet());
            else on.removeAll(command.range().toSet());
        }
    }

    public int size() {
        return on.size();
    }
}
