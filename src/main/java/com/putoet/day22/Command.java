package com.putoet.day22;

import com.putoet.grid.Point3D;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record Command(boolean on, @NotNull Range3D range) {
    public static List<Command> of(@NotNull List<String> lines) {
        return lines.stream().map(Command::of).toList();
    }

    public static Command of(@NotNull String line) {
        final var split = line.split(" ");
        if (split.length == 2)
            return new Command("on".equals(split[0]), Range3D.of(split[1]));

        throw new IllegalArgumentException("Invalid line " + line);
    }

    public static Range3D range(@NotNull List<Command> commands) {
        assert !commands.isEmpty();

        int min_x = Integer.MAX_VALUE, min_y = Integer.MAX_VALUE, min_z = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE, max_y = Integer.MIN_VALUE, max_z = Integer.MIN_VALUE;

        for (var command : commands) {
            min_x = Math.min(min_x, command.range.min().x());
            min_y = Math.min(min_y, command.range.min().y());
            min_z = Math.min(min_z, command.range.min().z());

            max_x = Math.max(max_x, command.range.max().x());
            max_y = Math.max(max_y, command.range.max().y());
            max_z = Math.max(max_z, command.range.max().z());
        }

        return new Range3D(Point3D.of(min_x, min_y, min_z), Point3D.of(max_x, max_y, max_z));
    }
}
