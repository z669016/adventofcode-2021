package com.putoet.day6;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class LanternFishModel {
    public static long progress(List<Integer> school, int days) {
        return school.stream().mapToLong(init -> progress(days, init)).sum();
    }

    private static long progress(int days, int init) {
        final long[] cache = new long[days];
        Arrays.fill(cache, -1);

        final int daysLeft = days - ((init + 1) % 7);
        return 1 + new Function<Integer,Long>() {
            @Override
            public Long apply(Integer daysLeft) {
                if (daysLeft < 0)
                    return 0L;

                if (daysLeft < 7)
                    return 1L;

                if (cache[daysLeft] != -1)
                    return cache[daysLeft];

                final long result =  1 +                         // count the current
                        this.apply(daysLeft - 7) +      // add the next cycle
                        this.apply(daysLeft - 9);       // add the offspring
                cache[daysLeft] = result;

                return result;
            }
        }.apply(daysLeft);
    }
}
