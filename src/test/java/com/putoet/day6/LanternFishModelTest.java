package com.putoet.day6;

import com.putoet.resources.CSV;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanternFishModelTest {

    @Test
    void progress() {
        final List<Integer> school = CSV.flatList("/day6.txt", Integer::parseInt);
        progressDays(school, 18, 26);
        progressDays(school, 80, 5934);
        progressDays(school, 256, 26984457539L);
    }

    private void progressDays(List<Integer> school, int days, long expectedSize) {
        final LanternFishModel model = new LanternFishModel(school, days);

        assertEquals(expectedSize, model.run());
    }

    @Test
    void test() {
        assertEquals(1401, new LanternFishModel(List.of(1), 80).run());
        assertEquals(1191, new LanternFishModel(List.of(2), 80).run());
        assertEquals(1154, new LanternFishModel(List.of(3), 80).run());
        assertEquals(1034, new LanternFishModel(List.of(4), 80).run());
    }
}