package com.lantopia.civgis.utils;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 *
 * A basic stack interface.  Better than Java's.
 */
public interface Stack<E> {
    void push(E element);
    E peek();
    E pop();
    int depth();
}
