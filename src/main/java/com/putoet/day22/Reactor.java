package com.putoet.day22;

import com.putoet.grid.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Reactor {
    public static long processLimited(List<Command> commands) {
        assert commands != null;
        if (commands.isEmpty())
            return 0;

        final Range3D limit = Range3D.of(Point3D.of(-50, -50, -50), Point3D.of(50, 50, 50));
        return process(commands.stream().filter(command -> limit.contains(command.range())).toList());

//        final Set<Point3D> on = new HashSet<>();
//        commands.forEach(command -> {
//            if (limit.contains(command.range())) {
//                if (command.on())
//                    on.addAll(command.range().toSet());
//                else
//                    on.removeAll(command.range().toSet());
//            }
//        });
//
//        return on.size();
    }

    public static long process(List<Command> commands) {
        assert commands != null;
        if (commands.isEmpty())
            return 0;

        List<Command> split = new ArrayList<>();
        split.add(commands.get(0));

        for (int i = 1; i < commands.size(); i++) {
            final Command command = commands.get(i);
            final List<Command> next = new ArrayList<>();
            next.add(command);

            for (Command toSplit : split) {
                if (toSplit.range().overlap(command.range())) {
                    final List<Range3D> splitRange = toSplit.range().carve(command.range());
                    splitRange.forEach(range -> next.add(new Command(toSplit.on(), range)));
                } else {
                    next.add(toSplit);
                }
            }
            split = next;
        }

        long size = 0;

        for (Command command : split) {
            if (command.on()) {
                size += command.range().point3DCount();
            }
        }

        return size;
    }
}
