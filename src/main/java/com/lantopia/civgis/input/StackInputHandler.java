package com.lantopia.civgis.input;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 */
@SuppressWarnings("UnusedDeclaration")
public class StackInputHandler implements InputHandler {
    // TODO: How concurrent does it really need to be?
    private final Deque<InputHandler> stack = new ConcurrentLinkedDeque<InputHandler>();

    StackInputHandler(final InputHandler baseHandler) {
        stack.push(baseHandler);
    }

    public void push(final InputHandler handler) {
        stack.push(handler);
    }

    public void pop() {
        stack.pop();
    }

    @Override
    public boolean handle(final KeyPattern pattern) {
        for (final InputHandler handler : stack) {
            if (handler.handle(pattern)) return true;
        }
        return false;
    }
}
