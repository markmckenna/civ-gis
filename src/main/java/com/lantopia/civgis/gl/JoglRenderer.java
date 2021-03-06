package com.lantopia.civgis.gl;

import com.lantopia.civgis.renderer.Renderer;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 14/02/14
 */
public class JoglRenderer implements GLEventListener {
    private Renderer delegate;

    JoglRenderer(final Renderer delegate) {
        this.delegate = delegate;
    }

    @Override
    public void init(final GLAutoDrawable drawable) { }

    @Override
    public void dispose(final GLAutoDrawable drawable) {
        delegate.stop();
    }

    @Override
    public void display(final GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {

    }
}
