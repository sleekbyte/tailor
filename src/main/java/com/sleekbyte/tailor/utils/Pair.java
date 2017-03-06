package com.sleekbyte.tailor.utils;

/**
 * Couples together a pair of values, which may be of different types (L and R).
 * The individual values can be accessed via its public getter functions getFirst() and getSecond().
 *
 * @param <L> Type of member first
 * @param <R> Type of member second
 */
public final class Pair<L, R> {
    private final L first;
    private final R second;

    public Pair(L left, R right) {
        this.first = left;
        this.second = right;
    }

    public L getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pairObject = (Pair) obj;
        return this.first.equals(pairObject.getFirst()) && this.second.equals(pairObject.getSecond());
    }

}
