package com.putoet.day13;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day13 extends Day {
    private final List<String> input;

    protected Day13(String[] args) {
        super(args);
        input = ResourceLines.list("/day13.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day13(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final FoldingInstruction instruction = FoldingInstruction.of(input).get(0);
        TransparentPaper paper = TransparentPaper.of(input);

        if (instruction.along() == FoldingInstruction.Along.X)
            paper = paper.foldLeft(instruction.offset());
        else
            paper = paper.foldUp(instruction.offset());

        System.out.println("The numer of dots after a single fold is " + paper.dots());
    }

    @Override
    public void part2() {
        final List<FoldingInstruction> instructions = FoldingInstruction.of(input);
        TransparentPaper paper = TransparentPaper.of(input);

        for (FoldingInstruction instruction :instructions) {
            if (instruction.along() == FoldingInstruction.Along.X)
                paper = paper.foldLeft(instruction.offset());
            else
                paper = paper.foldUp(instruction.offset());
        }

        System.out.println(paper);
    }
}
