package com.putoet.day20;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day20 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day20.txt");
        final var enhancer = new ImageEnhancer(lines.get(0).toCharArray());
        final var image = Image.of(lines.subList(2, lines.size()));

        Timer.run(() -> part1(enhancer, image));
        Timer.run(() -> part2(enhancer, image));
    }

    static void part1(ImageEnhancer enhancer, Image image) {
        final var enhanced = enhancer.enhance(image, 2);
        System.out.println("pixels are lit in the resulting image is " + enhanced.pixelsLit());
    }

    static void part2(ImageEnhancer enhancer, Image image) {
        final var enhanced = enhancer.enhance(image, 50);
        System.out.println("pixels are lit in the resulting image is " + enhanced.pixelsLit());
    }
}
