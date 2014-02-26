package com.lantopia.civgis.gl;

import com.lantopia.civgis.renderer.BaseRenderer;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
public abstract class GLRenderer extends BaseRenderer<GLAutoDrawable> implements GLEventListener {
    // TODO: Decouple GLEventListener instance from Screen instance

    @Override
    public final void init(final GLAutoDrawable drawable) {
        setRenderTarget(drawable);
    }

    // TODO: final
    @Override
    public void dispose(final GLAutoDrawable drawable) {
    }

    @Override
    public final void display(final GLAutoDrawable drawable) {
        super.cycle(drawable);
    }

    @Override
    public final void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) { }
}
