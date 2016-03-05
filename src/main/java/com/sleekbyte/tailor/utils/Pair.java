package com.sleekbyte.tailor.utils;

/**
 * Pair data structure.
 */
public final class Pair<L,R> {
    private final L first;
    private final R second;

    public Pair(L left, R right) {
        this.first = left;
        this.second = right;
    }

    public L getFirst() { return first; }
    public R getSecond() { return second; }

    @Override
    public int hashCode() { return first.hashCode() ^ second.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairObject = (Pair) o;
        return this.first.equals(pairObject.getFirst()) &&
            this.second.equals(pairObject.getSecond());
    }

}
