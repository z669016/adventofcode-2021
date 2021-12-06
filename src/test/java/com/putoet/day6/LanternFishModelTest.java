package com.putoet.day6;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanternFishModelTest {

    @Test
    void single() {
        assertEquals(1401, LanternFishModel.progress(List.of(1), 80));
        assertEquals(1191, LanternFishModel.progress(List.of(2), 80));
        assertEquals(1154, LanternFishModel.progress(List.of(3), 80));
        assertEquals(1034, LanternFishModel.progress(List.of(4), 80));
    }

    @Test
    void process() {
        assertEquals(26, LanternFishModel.progress(List.of(3, 4, 3, 1, 2), 18));
        assertEquals(5934, LanternFishModel.progress(List.of(3, 4, 3, 1, 2), 80));
        assertEquals(26984457539L, LanternFishModel.progress(List.of(3, 4, 3, 1, 2), 256));
    }

}