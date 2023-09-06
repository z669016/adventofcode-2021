package com.putoet.day6;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

public class Day6 {
    public static void main(String[] args) {
        final var school = CSV.flatList("/day6.txt", Integer::parseInt);

        Timer.run(() ->
                System.out.println("After 80 days the school has " + LanternFishModel.progress(school, 80) + " fish.")
        );

        Timer.run(() ->
                System.out.println("After 256 days the school has " + LanternFishModel.progress(school, 256) + " fish.")
        );
    }
}
