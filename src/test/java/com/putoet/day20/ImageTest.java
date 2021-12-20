package com.putoet.day20;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageTest {
    private static final String SMALL = """
            #..#.
            #....
            ##..#
            ..#..
            ..###""";


    @Test
    void of() {
        assertEquals(SMALL, Image.of(SMALL).toString());
    }
}