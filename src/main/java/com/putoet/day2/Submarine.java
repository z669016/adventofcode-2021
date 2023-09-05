package com.putoet.day2;

import java.util.List;

class Submarine {
    protected long depth;
    protected long horizontalPosition;

    public Submarine move(List<Command> command) {
        command.forEach(this::move);
        return this;
    }
    public Submarine move(Command command) {
        switch (command.direction()) {
            case FORWARD -> horizontalPosition += command.distance();
            case UP -> depth -= command.distance();
            case DOWN -> depth += command.distance();
        }

        return this;
    }

    public long depth() { return depth; }
    public long horizontalPosition() { return horizontalPosition; }
}
