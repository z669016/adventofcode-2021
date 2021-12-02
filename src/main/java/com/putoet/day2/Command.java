package com.putoet.day2;

import java.util.Objects;

public record Command(Direction direction, int distance) {

    public Command {
        Objects.requireNonNull(direction);
    }

    public static Command with(Direction direction, int distance) {
        return new Command(direction, distance);
    }

    public static Command of(String line) {
        assert line != null;
        assert line.split(" ").length == 2;

        final String[] split = line.split(" ");
        final Direction direction = switch (split[0]) {
            case "forward" -> Direction.FORWARD;
            case "down" -> Direction.DOWN;
            case "up" ->Direction.UP;
            default -> throw new IllegalArgumentException("Invalid command '" + line +"'");
        };
        final int distance = Integer.parseInt(split[1]);

        return new Command(direction, distance);
    }
}
