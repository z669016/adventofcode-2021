package com.putoet.day4;

import java.util.List;
import java.util.Optional;

public class Bingo {
    private final List<BingoBoard> boards;

    public Bingo(List<BingoBoard> boards) {
        this.boards = boards;
    }

    public Optional<BingoBoard> play(List<Integer> numbers) {
        for (Integer number : numbers) {
            for (BingoBoard board : boards) {
                if (board.call(number))
                    return Optional.of(board);
            }
        }
        return Optional.empty();
    }

    public Optional<BingoBoard> playForLast(List<Integer> numbers) {
        for (Integer number : numbers) {
            for (BingoBoard board : boards) {
                if (board.call(number) && boards.size() == 1)
                    return Optional.of(board);
            }
            boards.removeIf(board -> board.rowComplete() || board.columnComplete());
        }
        return Optional.empty();
    }
}
