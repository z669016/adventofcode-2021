package com.putoet.day25.framework;

import com.putoet.day25.application.Day25OutputPort;
import com.putoet.day25.domain.SeaFloor;
import com.putoet.day25.domain.SeaFloorFactoryPolicy;

public record StringAdapter(String map) implements Day25OutputPort {
    @Override
    public SeaFloor input() {
        return SeaFloorFactoryPolicy.from(map);
    }
}
