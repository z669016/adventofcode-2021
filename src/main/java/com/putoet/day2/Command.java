package com.putoet.day2;

import java.util.Objects;

public class Command {
    public final Direction direction;
    public final int distance;

    public Command(Direction direction, int distance) {
        this.direction = direction;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return distance == command.distance && direction == command.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, distance);
    }

    @Override
    public String toString() {
        return "Command{" + direction + ", " + distance + '}';
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
