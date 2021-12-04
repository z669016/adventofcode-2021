package com.putoet.day4;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.*;
import java.util.stream.Collectors;

public class Day4 extends Day {
    private List<BingoBoard> boards;
    private List<Integer> numbers;

    protected Day4(String[] args) {
        super(args);
    }

    private void init() {
        final List<String> lines = ResourceLines.list("/day4.txt");
        numbers = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        boards = new ArrayList<>();
        for (int i = 2; i < lines.size(); i += 6) {
            boards.add(BingoBoard.of(lines.subList(i, i + 5)));
        }
    }

    public static void main(String[] args) {
        final Day day = new Day4(args);
        day.challenge(args);
    }

    @Override
    public void part1() {
        final Optional<BingoBoard> board = play();
        if (board.isPresent())
            System.out.println("The score is " + board.get().score());
        else
            System.out.println("No winner in this game");
    }

    @Override
    public void part2() {
        final Optional<BingoBoard> board = playForLast();
        if (board.isPresent())
            System.out.println("The last score is " + board.get().score());
        else
            System.out.println("No winner in this game");
    }

    public Optional<BingoBoard> play() {
        init();
        final Bingo bingo = new Bingo(boards);
        return bingo.play(numbers);
    }

    public Optional<BingoBoard> playForLast() {
        init();
        final Bingo bingo = new Bingo(boards);
        return bingo.playForLast(numbers);
    }

}
