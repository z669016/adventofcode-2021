package com.putoet.day25.application;

import com.putoet.day25.domain.SeaFloorTimeService;

public record Day25InputPort(Day25OutputPort outputPort) implements Day25UseCase {
    @Override
    public int firstStepOnWhichNoSeaCucumbersMove() {
        final var initial = outputPort.input();
        int step = 1;

        var next = SeaFloorTimeService.advance(initial, 1);
        while (next.getValue1() != 0) {
            step++;
            next = SeaFloorTimeService.advance(next.getValue0(), 1);
        }

        return step;
    }
}
