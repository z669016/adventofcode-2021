package com.putoet.day13;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day13  {
    public static void main(String[] args) {
        final var input = ResourceLines.list("/day13.txt");
        final List<FoldingInstruction> instructions = FoldingInstruction.of(input);
        final var paper = TransparentPaper.of(input);

        Timer.run(() -> part1(instructions.get(0), paper));
        Timer.run(() -> part2(instructions, paper));
    }

    static void part1(FoldingInstruction instruction, TransparentPaper paper) {
        if (instruction.along() == FoldingInstruction.Along.X)
            paper = paper.foldLeft(instruction.offset());
        else
            paper = paper.foldUp(instruction.offset());

        System.out.println("The numer of dots after a single fold is " + paper.dots());
    }

    static void part2(List<FoldingInstruction> instructions, TransparentPaper paper) {
        for (FoldingInstruction instruction :instructions) {
            if (instruction.along() == FoldingInstruction.Along.X)
                paper = paper.foldLeft(instruction.offset());
            else
                paper = paper.foldUp(instruction.offset());
        }

        System.out.println(paper);
    }
}
