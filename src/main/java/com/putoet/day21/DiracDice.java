package com.putoet.day21;

public class DiracDice {
    private long score1;
    private long score2;
    private int pos1;
    private int pos2;
    private long turn;
    private boolean flip = true;
    final Die die = new Die();

    public DiracDice(int startPos1, int startPos2) {
        pos1 = startPos1;
        pos2 = startPos2;
    }

    void play() {
        while (score1 < 1000 && score2 < 1000) {
            if (flip) {
                pos1 = pos1 + die.get();
                if (pos1 > 10)
                    pos1 -= 10;
                score1 += pos1;
            } else {
                pos2 = pos2 + die.get();
                if (pos2 > 10)
                    pos2 -= 10;
                score2 += pos2;
            }
            flip = !flip;
        }
    }

    public long looserScore() {
        return Math.min(score1, score2);
    }

    public int turns() {
        return die.turns();
    }
}
