package com.putoet.day18;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Triple;

public class RegularSnailFishNumber implements SnailFishNumber {
    private SnailFishNumber parent;
    private int value;

    public RegularSnailFishNumber(int value) {
        this.value = value;
    }

    @Override
    public SnailFishNumber parent() {
        return parent;
    }

    @Override
    public void setParent(SnailFishNumber parent) {
        this.parent = parent;
    }

    @Override
    public long magnitude() {
        return value;
    }

    @Override
    public SnailFishNumber add(SnailFishNumber number) {
        if (number instanceof RegularSnailFishNumber other)
            this.value = this.value + other.value;
        else
            throw new IllegalArgumentException("Cannot add " + number + " to " + this);

        return this;
    }

    @Override
    public boolean canSplit() {
        return value >= 10;
    }

    @Override
    public SnailFishNumber split() {
        if (!canSplit())
            throw new IllegalStateException("Cannot split value " + value);

        return SnailFishNumbers.of(value / 2, value - (value / 2));
    }

    @Override
    public boolean canExplode() {
        return false;
    }

    @Override
    public Triple<SnailFishNumber,SnailFishNumber,SnailFishNumber> explode() {
        throw new NotImplementedException();
    }

    @Override
    public boolean reduceExplosion() {
        return false;
    }

    @Override
    public boolean reduceSplit() {
        return false;
    }

    @Override
    public void moveRightFrom(SnailFishNumber number, SnailFishNumber from) {
        assert number != null;
        assert number instanceof RegularSnailFishNumber;
        assert from != null;
        assert from.equals(parent);

        add(number);
    }

    @Override
    public void moveLeftFrom(SnailFishNumber number, SnailFishNumber from) {
        moveRightFrom(number, from);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
