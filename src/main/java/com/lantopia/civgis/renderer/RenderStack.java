package com.lantopia.civgis.renderer;

import com.lantopia.civgis.utils.FlooredStack;

import java.util.Iterator;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 *
 * A stack of renderers.  Each renderer is either opaque or not opaque.  The render process starts at the highest
 * stack frame that is opaque and proceeds upward in sequence.
 */
public class RenderStack<T> extends FlooredStack<Renderer<T>> {
    private T target;
    private Renderer<T> highestOpaque;

    RenderStack(final T target, final Renderer<T> floor) {
        super(floor);
        this.target = target;
        if (!floor.isOpaque()) throw new IllegalArgumentException("Bottommost Renderer must be opaque");
        highestOpaque = floor;
    }

    @Override
    public void push(final Renderer<T> r) {
        super.push(r);
        if (r.isOpaque()) highestOpaque = r;
    }

    @Override
    public Renderer<T> pop() {
        final Renderer<T> out = super.pop();
        if (out.isOpaque()) {
            for (final Renderer<T> r : this) {
                if (r.isOpaque()) {
                    highestOpaque = r;
                    break;
                }
            }
        }
        return out;
    }

    void draw() {
        // TODO: Stop the render at the highest opaque Renderer instance
        for (final Iterator<Renderer<T>> i = this.descendingIterator(); i.hasNext();) {
            i.next().render(target);
        }
    }
}
