package com.putoet.day18;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

class CompoundSnailFishNumber implements SnailFishNumber {
    private SnailFishNumber left;
    private SnailFishNumber right;
    private SnailFishNumber parent;

    public CompoundSnailFishNumber(@NotNull SnailFishNumber left, @NotNull SnailFishNumber right) {
        this.left = left;
        this.right = right;

        this.left.setParent(this);
        this.right.setParent(this);
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", left, right);
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
        return 3 * left.magnitude() + 2 * right.magnitude();
    }

    @Override
    public SnailFishNumber add(@NotNull SnailFishNumber other) {
        final var compound = new CompoundSnailFishNumber(this, other);
        this.setParent(compound);
        other.setParent(compound);

        return compound;
    }

    @Override
    public boolean canSplit() {
        return false;
    }

    @Override
    public SnailFishNumber split() {
        throw new NotImplementedException();
    }

    @Override
    public boolean canExplode() {
        return depth() > 3;
    }

    @Override
    public Triple<SnailFishNumber, SnailFishNumber, SnailFishNumber> explode() {
        if (!canExplode())
            throw new IllegalStateException("Cannot explode " + this);

        assert left instanceof RegularSnailFishNumber;
        assert right instanceof RegularSnailFishNumber;

        return Triple.of(left, right, SnailFishNumbers.of(0));
    }

    public boolean reduceExplosion() {
        if (left.reduceExplosion())
            return true;

        if (right.reduceExplosion())
            return true;

        if (left.canExplode()) {
            final var triple = left.explode();
            left = triple.getRight();
            left.setParent(this);
            parent.moveLeftFrom(triple.getLeft(), this);
            right.moveLeftFrom(triple.getMiddle(), this);
            return true;
        }

        if (right.canExplode()) {
            final var triple = right.explode();
            right = triple.getRight();
            right.setParent(this);
            parent.moveRightFrom(triple.getMiddle(), this);
            left.moveRightFrom(triple.getLeft(), this);
            return true;
        }

        return false;
    }

    @Override
    public boolean reduceSplit() {
        if (left.canSplit()) {
            left = left.split();
            left.setParent(this);
            return true;
        }

        if (left.reduceSplit())
            return true;

        if (right.canSplit()) {
            right = right.split();
            right.setParent(this);
            return true;
        }

        return right.reduceSplit();
    }

    @Override
    public void moveRightFrom(@NotNull SnailFishNumber number, @NotNull SnailFishNumber from) {
        if (from.equals(parent))
            right.moveRightFrom(number, this);

        if (from.equals(left))
            right.moveLeftFrom(number, this);

        if (from.equals(right) && (parent != null))
            parent.moveRightFrom(number, this);
    }

    @Override
    public void moveLeftFrom(@NotNull SnailFishNumber number, @NotNull SnailFishNumber from) {
        if (from.equals(parent))
            left.moveLeftFrom(number, this);

        if (from.equals(right))
            left.moveRightFrom(number, this);

        if (from.equals(left) && (parent != null))
            parent.moveLeftFrom(number, this);
    }
}
