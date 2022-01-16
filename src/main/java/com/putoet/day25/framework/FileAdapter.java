package com.putoet.day25.framework;

import com.putoet.day25.application.Day25OutputPort;
import com.putoet.day25.domain.SeaFloor;
import com.putoet.day25.domain.SeaFloorFactoryPolicy;
import com.putoet.resources.ResourceLines;

public record FileAdapter(String resource) implements Day25OutputPort {
    @Override
    public SeaFloor input() {
        var lines = ResourceLines.list(resource);
        return SeaFloorFactoryPolicy.from(lines);
    }
}
