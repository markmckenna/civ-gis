package com.lantopia.civgis.utils;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 *
 * Implements a stack whose base element cannot be eliminated.
 */
public class FlooredStack<T> implements Stack<T>, Iterable<T> {
    public static <T> FlooredStack make(final T floor) { return new FlooredStack<T>(floor); }

    private final LinkedList<T> stack = new LinkedList<T>();
    private volatile int depth;

    protected FlooredStack(final T floor) {
        if (floor==null) throw new IllegalArgumentException("Must provide base element to FlooredStack");
        stack.push(floor);
        depth = 1;
    }

    @Override
    public void push(final T element) {
        stack.push(element);
        ++depth;
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public T pop() {
        if (depth==1) throw new IllegalStateException("Cannot pop base element");
        return stack.pop();
    }

    @Override
    public int depth()  { return depth; }

    public Iterator<T> iterator() { return stack.iterator(); }
    public Iterator<T> descendingIterator() { return stack.descendingIterator(); }
}
