package com.lantopia.civgis;

import com.lantopia.civgis.gl.GLRenderable;
import com.lantopia.civgis.utils.Log;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
public class Demo extends GLRenderable {
    private Log log = new Log(Demo.class.getSimpleName());

    @Override
    public void update(final long now, final long millisSinceLastUpdate) {
        log.d("update called (now=%d, delta=%d", now, millisSinceLastUpdate);
    }

    @Override
    public void render(final GLAutoDrawable renderTarget) {
        log.d("render called");

        final GL2 gl = renderTarget.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1, 0, 0);
        gl.glVertex2f(-1, -1);
        gl.glColor3f(0, 1, 0);
        gl.glVertex2f(0, 1);
        gl.glColor3f(0, 0, 1);
        gl.glVertex2f(1, -1);
        gl.glEnd();
    }
}
