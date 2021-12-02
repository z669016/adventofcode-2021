package com.putoet.day2;

public class Submarine2 extends Submarine {
    private long aim;

    public Submarine move(Command command) {
        switch (command.direction()) {
            case FORWARD -> {
                horizontalPosition += command.distance();
                depth += (aim * command.distance());
            }
            case UP -> aim -= command.distance();
            case DOWN -> aim += command.distance();
        }

        return this;
    }
}
