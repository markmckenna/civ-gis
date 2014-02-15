package com.lantopia.civgis.gl;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
public abstract class GLRenderable extends BaseRenderable<GLAutoDrawable> implements GLEventListener {
    @Override
    public final void init(final GLAutoDrawable drawable) { }

    @Override
    public final void dispose(final GLAutoDrawable drawable) { }

    @Override
    public final void display(final GLAutoDrawable drawable) {
        super.cycle(drawable);
    }

    @Override
    public final void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) { }
}
