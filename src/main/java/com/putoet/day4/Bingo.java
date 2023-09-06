package com.putoet.day4;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

record Bingo(@NotNull List<BingoCard> cards) {
    public Bingo {
        assert !cards.isEmpty();
    }

    public Optional<BingoCard> play(@NotNull List<Integer> numbers) {

        for (var number : numbers) {
            for (var card : cards) {
                if (card.call(number))
                    return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    public Optional<BingoCard> playForLast(@NotNull List<Integer> numbers) {
        for (var number : numbers) {
            for (var board : cards) {
                if (board.call(number) && cards.size() == 1)
                    return Optional.of(board);
            }
            cards.removeIf(BingoCard::complete);
        }
        return Optional.empty();
    }
}
