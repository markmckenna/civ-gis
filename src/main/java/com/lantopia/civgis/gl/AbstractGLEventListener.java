package com.lantopia.civgis.gl;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
@SuppressWarnings("UnusedDeclaration")
public class AbstractGLEventListener implements GLEventListener {
    public void init(final GLAutoDrawable drawable) {  }
    public void dispose(final GLAutoDrawable drawable) {  }
    public void display(final GLAutoDrawable drawable) {  }
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {  }
}
