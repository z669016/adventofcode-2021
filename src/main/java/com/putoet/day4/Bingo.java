package com.putoet.day4;

import java.util.List;
import java.util.Optional;

public record Bingo(List<BingoCard> cards) {
    public Bingo {
        assert cards != null;
        assert !cards.isEmpty();
    }

    public Optional<BingoCard> play(List<Integer> numbers) {
        assert numbers != null;

        for (Integer number : numbers) {
            for (BingoCard card : cards) {
                if (card.call(number))
                    return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    public Optional<BingoCard> playForLast(List<Integer> numbers) {
        assert numbers != null;

        for (Integer number : numbers) {
            for (BingoCard board : cards) {
                if (board.call(number) && cards.size() == 1)
                    return Optional.of(board);
            }
            cards.removeIf(BingoCard::complete);
        }
        return Optional.empty();
    }
}
