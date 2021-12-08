package com.putoet.day8;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DigitsTest {
    @Test
    void mapper() {
        final String encoding = "abcdefg";
        final Map<String, Integer> map = Digits.mapper(encoding);

        assertEquals(0, map.get(Digits.SEGMENTS.get(0)));
        assertEquals(1, map.get(Digits.SEGMENTS.get(1)));
        assertEquals(2, map.get(Digits.SEGMENTS.get(2)));
        assertEquals(3, map.get(Digits.SEGMENTS.get(3)));
        assertEquals(4, map.get(Digits.SEGMENTS.get(4)));
        assertEquals(5, map.get(Digits.SEGMENTS.get(5)));
        assertEquals(6, map.get(Digits.SEGMENTS.get(6)));
        assertEquals(7, map.get(Digits.SEGMENTS.get(7)));
        assertEquals(8, map.get(Digits.SEGMENTS.get(8)));
        assertEquals(9, map.get(Digits.SEGMENTS.get(9)));
    }

    @Test
    void decode() {
        final String encoding = "deafgbc";

        assertEquals(8, Digits.decode(encoding, "acedgfb").getAsInt());
        assertEquals(5, Digits.decode(encoding, "cdfbe").getAsInt());
        assertEquals(2, Digits.decode(encoding, "gcdfa").getAsInt());
        assertEquals(3, Digits.decode(encoding, "fbcad").getAsInt());
        assertEquals(7, Digits.decode(encoding, "dab").getAsInt());
        assertEquals(9, Digits.decode(encoding, "cefabd").getAsInt());
        assertEquals(6, Digits.decode(encoding, "cdfgeb").getAsInt());
        assertEquals(4, Digits.decode(encoding, "eafb").getAsInt());
        assertEquals(0, Digits.decode(encoding, "cagedb").getAsInt());
        assertEquals(1, Digits.decode(encoding, "ab").getAsInt());
    }

    @Test
    void encoding() {
        final String encoding = "deafgbc";

        assertEquals(encoding, Digits.encoding(List.of("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab")));
        assertEquals(5353, Digits.decode(encoding, List.of("cdfeb", "fcadb", "cdfeb", "fcadb")).getAsInt());
    }
}