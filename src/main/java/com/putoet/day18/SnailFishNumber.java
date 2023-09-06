package com.putoet.day18;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

interface SnailFishNumber {
    SnailFishNumber parent();

    void setParent(SnailFishNumber parent);

    long magnitude();

    SnailFishNumber add(@NotNull SnailFishNumber other);

    boolean canSplit();

    SnailFishNumber split();

    boolean canExplode();

    Triple<SnailFishNumber, SnailFishNumber, SnailFishNumber> explode();

    default boolean reduce() {
        var reduced = false;
        do {
            reduced = reduceExplosion();
            if (!reduced)
                reduced = reduceSplit();
        } while (reduced);

        return false;
    }

    boolean reduceExplosion();
    boolean reduceSplit();

    void moveRightFrom(@NotNull SnailFishNumber number, @NotNull SnailFishNumber from);

    void moveLeftFrom(@NotNull SnailFishNumber number, @NotNull SnailFishNumber from);

    default int depth() {
        var depth = 0;
        var p = parent();
        while (p != null) {
            depth++;
            p = p.parent();
        }

        return depth;
    }
}
