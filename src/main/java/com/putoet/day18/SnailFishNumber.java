package com.putoet.day18;

import org.apache.commons.lang3.tuple.Triple;

public interface SnailFishNumber {
    SnailFishNumber parent();

    void setParent(SnailFishNumber parent);

    long magnitude();

    SnailFishNumber add(SnailFishNumber other);

    boolean canSplit();

    SnailFishNumber split();

    boolean canExplode();

    Triple<SnailFishNumber, SnailFishNumber, SnailFishNumber> explode();

    default boolean reduce() {
        boolean reduced;
        do {
            reduced = reduceExplosion();
            if (!reduced)
                reduced = reduceSplit();
        } while (reduced);

        return false;
    }

    boolean reduceExplosion();
    boolean reduceSplit();

    void moveRightFrom(SnailFishNumber number, SnailFishNumber from);

    void moveLeftFrom(SnailFishNumber number, SnailFishNumber from);

    default int depth() {
        int depth = 0;
        SnailFishNumber p = parent();
        while (p != null) {
            depth++;
            p = p.parent();
        }

        return depth;
    }
}
