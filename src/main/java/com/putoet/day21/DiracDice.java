package com.putoet.day21;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

class DiracDice {
    private final boolean quantum;
    private final Die die;
    private int positionOne;
    private int positionTwo;
    private boolean turnOne = true;

    public DiracDice(int startPos1, int startPos2, @NotNull Die die) {
        this.positionOne = startPos1;
        this.positionTwo = startPos2;
        this.die = die;

        this.quantum = die instanceof QuantumDie;
    }

    private static int nextPosition(int position, int diceRoll) {
        return ((position - 1 + diceRoll) % 10) + 1;
    }

    public Pair<BigInteger, BigInteger> play(int endScore) {
        if (!quantum)
            return playRegular(endScore);
        else
            return playQuantum(endScore);
    }

    private Pair<BigInteger, BigInteger> playRegular(int endScore) {
        var score1 = 0L;
        var score2 = 0L;
        while (score1 < endScore && score2 < endScore) {
            if (turnOne) {
                positionOne = nextPosition(positionOne, die.get() % 10);
                score1 += positionOne;
            } else {
                positionTwo = nextPosition(positionTwo, die.get() % 10);
                score2 += positionTwo;
            }
            turnOne = !turnOne;
        }

        return Pair.with(BigInteger.valueOf(score1), BigInteger.valueOf(score2));
    }

    private Pair<BigInteger, BigInteger> playQuantum(int endScore) {
        final var cache = new HashMap<String, Pair<BigInteger, BigInteger>>();
        return playQuantum(cache, 0, 0, positionOne, positionTwo, 0, 0, endScore);
    }

    private Pair<BigInteger, BigInteger> playQuantum(Map<String, Pair<BigInteger, BigInteger>> cache,
                                                     int scoreOne,
                                                     int scoreTwo,
                                                     int positionOne,
                                                     int positionTwo,
                                                     int turn,
                                                     int throwSum,
                                                     int endScore) {

        // a new universe was started after each throw, continue with playerOne if turn < 3
        var turnOne = turn < 3;

        // Return (1,0) if player one has won
        if (scoreOne >= endScore)
            return Pair.with(BigInteger.ONE, BigInteger.ZERO);

        // Return (0,1) if player two has won
        if (scoreTwo >= endScore)
            return Pair.with(BigInteger.ZERO, BigInteger.ONE);

        // calculate the cache key and check if the result of the game is already known; if so return the known value
        final String cacheKey = String.format("%d-%d-%d-%d-%d-%d", positionOne, scoreOne, positionTwo, scoreTwo, turn, throwSum);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        // the winner count starts at 0 for each universe
        var winsOne = BigInteger.ZERO;
        var winsTwo = BigInteger.ZERO;

        // if turn == 2 this is the last turn for player one, and his score must be updated
        var lastThrowOne = turn == 2;
        // if turn == 5 this is the last turn for player two, and his score must be updated
        var lastThrowTwo = turn == 5;

        // calculate the turn for the next universe
        var nextTurn = (turn + 1) % 6;

        // roll the dice three times and create three new universes
        for (var roll = 1; roll <= 3; roll++) {

            // get the result from the new universe
            final var pair = playQuantum(
                    cache,
                    turnOne && lastThrowOne ? scoreOne + nextPosition(positionOne, roll) : scoreOne,
                    !turnOne && lastThrowTwo ? scoreTwo + nextPosition(positionTwo, roll) : scoreTwo,
                    turnOne ? nextPosition(positionOne, roll) : positionOne,
                    !turnOne ? nextPosition(positionTwo, roll) : positionTwo,
                    nextTurn,
                    turn % 3 == 0 ? 0 : throwSum + roll,
                    endScore
            );

            // update the totals
            winsOne = winsOne.add(pair.getValue0());
            winsTwo = winsTwo.add(pair.getValue1());
        }

        // cache the results
        final var pair = Pair.with(winsOne, winsTwo);
        cache.put(cacheKey, pair);

        return pair;
    }

    public int turns() {
        return die.turns();
    }
}
