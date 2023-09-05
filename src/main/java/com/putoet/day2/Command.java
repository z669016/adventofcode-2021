package com.putoet.day2;

import org.jetbrains.annotations.NotNull;

record Command(@NotNull Direction direction, int distance) {
    public static Command of(@NotNull String line) {
        assert line.split(" ").length == 2;

        final var split = line.split(" ");
        final var direction = switch (split[0]) {
            case "forward" -> Direction.FORWARD;
            case "down" -> Direction.DOWN;
            case "up" ->Direction.UP;
            default -> throw new IllegalArgumentException("Invalid command '" + line +"'");
        };
        final var distance = Integer.parseInt(split[1]);

        return new Command(direction, distance);
    }
}
