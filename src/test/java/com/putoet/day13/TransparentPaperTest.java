package com.putoet.day13;

import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransparentPaperTest {
    private TransparentPaper paper;

    @BeforeEach
    void setup() {
        final List<String> input = ResourceLines.list("/day13.txt");
        paper = TransparentPaper.of(input);

        assertEquals(18, paper.dots());
    }

    @Test
    void asPoint() {
        assertEquals(Point.of(1, 3), TransparentPaper.asPoint(" 1,3 "));

        assertThrows(AssertionError.class , () -> TransparentPaper.asPoint(null));
        assertThrows(NumberFormatException.class , () -> TransparentPaper.asPoint("a,1"));
        assertThrows(NumberFormatException.class , () -> TransparentPaper.asPoint("1,a"));
    }

    @Test
    void of() {
        final String PAPER = """
                ...#..#..#.
                ....#......
                ...........
                #..........
                ...#....#.#
                ...........
                ...........
                ...........
                ...........
                ...........
                .#....#.##.
                ....#......
                ......#...#
                #..........
                #.#........
                """;
        assertEquals(PAPER, paper.toString());
    }

    @Test
    void foldAlongY() {
        final String PAPER = """
                #.##..#..#.
                #...#......
                ......#...#
                #...#......
                .#.#..#.###
                ...........
                ...........
                """;

        paper = paper.fold(FoldingInstruction.of(FoldingInstruction.Along.Y, 7));
        assertEquals(PAPER, paper.toString());
        assertEquals(17, paper.dots());
    }

    @Test
    void foldAlongX() {
        final String PAPER = """
                #####
                #...#
                #...#
                #...#
                #####
                .....
                .....
                """;

        final List<FoldingInstruction> instructions = List.of(
                FoldingInstruction.of(FoldingInstruction.Along.Y, 7),
                FoldingInstruction.of(FoldingInstruction.Along.X, 5)
        );

        paper = paper.fold(instructions);
        assertEquals(PAPER, paper.toString());
        assertEquals(16, paper.dots());
    }

}