package com.putoet.day20;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day20 extends Day {
    private final ImageEnhancer enhancer;
    private final Image image;

    protected Day20(String[] args) {
        super(args);

        final List<String> lines = ResourceLines.list("/day20.txt");
        enhancer = new ImageEnhancer(lines.get(0).toCharArray());
        image = Image.of(lines.subList(2, lines.size()));
    }

    public static void main(String[] args) {
        final Day day = new Day20(args);
        day.challenge();
    }

    @Override
    public void part1() {
        System.out.println(image.size());
        final Image enhanced = enhancer.enhance(image, 2);
        System.out.println("pixels are lit in the resulting image is " + enhanced.pixelsLit());
        System.out.println(enhanced.size());
    }

    @Override
    public void part2() {
        System.out.println(image.size());
        final Image enhanced = enhancer.enhance(image, 50);
        System.out.println("pixels are lit in the resulting image is " + enhanced.pixelsLit());
        System.out.println(enhanced.size());
    }
}
