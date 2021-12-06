package com.putoet.day6;

import java.util.Arrays;
import java.util.List;

public class LanternFishModel {
    private final List<Integer> school;
    private final int days;
    private final long[] cache;

    public LanternFishModel(List<Integer> school, int days) {
        assert school != null;
        assert days > 0;

        this.school = school;
        this.days = days;
        this.cache = new long[257];

        Arrays.fill(cache, -1);
    }

    public long run() {
        return school.stream().mapToLong(init ->repeatsFor(days, init)).sum();
    }

    protected long repeatsFor(int daysLeft, int init) {
        daysLeft = daysLeft - ((init + 1) % 7);
        return 1 + repeatsFor(daysLeft);
    }

    private long repeatsFor(int daysLeft) {
        if (daysLeft < 0)
            return 0;

        if (daysLeft < 7)
            return 1;

        if (cache[daysLeft] != -1)
            return cache[daysLeft];

        final long result =  1 + repeatsFor(daysLeft - 9) + repeatsFor(daysLeft - 7);
        cache[daysLeft] = result;

        return result;
    }
}
