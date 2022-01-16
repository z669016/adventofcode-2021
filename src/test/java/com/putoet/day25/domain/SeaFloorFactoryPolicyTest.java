package com.putoet.day25.domain;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeaFloorFactoryPolicyTest {

    @Test
    void from() {
        final List<String> map = ResourceLines.list("/day25.txt");
        final SeaFloor floor = SeaFloorFactoryPolicy.from(map);
        assertEquals(map, Arrays.asList(floor.toString().split("\n")));
    }
}