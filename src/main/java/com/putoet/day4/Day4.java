package com.putoet.day4;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day4 extends Day {
    private List<BingoCard> cards;
    private List<Integer> numbers;

    protected Day4(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day4(args);
        day.challenge();
    }

    private void init() {
        final List<String> lines = ResourceLines.list("/day4.txt");
        numbers = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        cards = new ArrayList<>();
        for (int i = 2; i < lines.size(); i += 6) {
            cards.add(BingoCard.of(lines.subList(i, i + 5)));
        }
    }

    @Override
    public void part1() {
        final Optional<BingoCard> card = play();
        if (card.isPresent())
            System.out.println("The first cards' score is " + card.get().score());
        else
            System.out.println("No first winner in this game");
    }

    @Override
    public void part2() {
        final Optional<BingoCard> card = playForLast();
        if (card.isPresent())
            System.out.println("The last cards' score is " + card.get().score());
        else
            System.out.println("No last winner in this game");
    }

    public Optional<BingoCard> play() {
        init();
        final Bingo bingo = new Bingo(cards);
        return bingo.play(numbers);
    }

    public Optional<BingoCard> playForLast() {
        init();
        final Bingo bingo = new Bingo(cards);
        return bingo.playForLast(numbers);
    }
}
