package com.putoet.day4;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day4 {
    private static List<BingoCard> cards;
    private static List<Integer> numbers;

    public static void main(String[] args) {
        Timer.run(Day4::part1);
        Timer.run(Day4::part2);
    }

    static void part1() {
        final var card = play().orElseThrow();
        System.out.println("The first cards' score is " + card.score());
    }

    static void part2() {
        final var card = playForLast().orElseThrow();
        System.out.println("The last cards' score is " + card.score());
    }

    static Optional<BingoCard> play() {
        init();
        final var bingo = new Bingo(cards);
        return bingo.play(numbers);
    }

    static Optional<BingoCard> playForLast() {
        init();
        final var bingo = new Bingo(cards);
        return bingo.playForLast(numbers);
    }

    static void init() {
        final var lines = ResourceLines.list("/day4.txt");
        numbers = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        cards = new ArrayList<>();
        for (var i = 2; i < lines.size(); i += 6) {
            cards.add(BingoCard.of(lines.subList(i, i + 5)));
        }
    }
}
