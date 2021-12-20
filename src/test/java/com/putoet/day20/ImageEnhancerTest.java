package com.putoet.day20;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageEnhancerTest {
    private static final String STEP1 = """
            .##.##.
            #..#.#.
            ##.#..#
            ####..#
            .#..##.
            ..##..#
            ...#.#.""";

    public static final String STEP2= """
            .......#.
            .#..#.#..
            #.#...###
            #...##.#.
            #.....#.#
            .#.#####.
            ..#.#####
            ...##.##.
            ....###..""";

    private Image image;
    private ImageEnhancer enhancer;

    @BeforeEach
    void setup() {
        final List<String> lines = ResourceLines.list("/day20.txt");
        enhancer = new ImageEnhancer(lines.get(0).toCharArray());
        image = Image.of(lines.subList(2, lines.size()));
    }

    @Test
    void forChar() {
        final String[] numbers = new String[] {"...", "..#", ".#.", ".##", "#..", "#.#", "##.", "###"};
        int value = 0;
        for (String x : numbers) {
            for (String y : numbers) {
                for (String z : numbers) {
                    String number = x + y + z;
                    assertEquals(value, ImageEnhancer.index(number.toCharArray()));
                    value++;
                }
            }
        }
    }

    @Test
    void enhance() {
        final Image enhanced = enhancer.enhance(image);
        assertEquals(STEP1, enhanced.toString());
    }

    @Test
    void enhanceTwice() {
        final Image enhanced = enhancer.enhance(image, 2);
        assertEquals(STEP2, enhanced.toString());
        assertEquals(35, enhanced.pixelsLit());
    }

    @Test
    void enhanceMore() {
        final Image enhanced = enhancer.enhance(image, 50);
        assertEquals(3351, enhanced.pixelsLit());
    }
}