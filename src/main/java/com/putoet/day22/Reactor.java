package com.putoet.day22;

import com.putoet.grid.Point3D;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class Reactor {
    public static long processLimited(@NotNull List<Command> commands) {
        if (commands.isEmpty())
            return 0;

        final var limit = Range3D.of(Point3D.of(-50, -50, -50), Point3D.of(50, 50, 50));
        return process(commands.stream().filter(command -> limit.contains(command.range())).toList());
    }

    public static long process(@NotNull List<Command> commands) {
        if (commands.isEmpty())
            return 0;

        var split = new ArrayList<Command>();
        split.add(commands.get(0));

        for (int i = 1; i < commands.size(); i++) {
            final var command = commands.get(i);
            final var next = new ArrayList<Command>();
            next.add(command);

            for (var toSplit : split) {
                if (toSplit.range().overlap(command.range())) {
                    final var splitRange = toSplit.range().carve(command.range());
                    splitRange.forEach(range -> next.add(new Command(toSplit.on(), range)));
                } else {
                    next.add(toSplit);
                }
            }
            split = next;
        }

        var size = 0L;
        for (var command : split) {
            if (command.on()) {
                size += command.range().point3DCount();
            }
        }

        return size;
    }
}
