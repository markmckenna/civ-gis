package com.lantopia.civgis;

import com.lantopia.civgis.gl.GLRenderer;
import com.lantopia.civgis.utils.Log;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
public class Demo extends GLRenderer {
    private Log log = new Log(Demo.class.getSimpleName());

    private GL2 gl;

    @Override
    public boolean isOpaque() { return true; }

    @Override
    public void initialize(final GLAutoDrawable renderTarget) {
        log.i("Initializing Demo");

        // TODO: In constructor
        gl = renderTarget.getGL().getGL2();

        // Set 'background color' to black
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // set camera perspective
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        // TODO: set perspective matrix
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();

        // enable smooth shading
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
    }

    private float xRotation;
    private float yRotation;
    private float zRotation;

    @Override
    public void update(final long now, final long millisSinceLastUpdate) {
        log.d("update called (now=%d, delta=%d", now, millisSinceLastUpdate);
        xRotation = ((now % 10000)/10000.0f)*360;
        yRotation = ((now % 10000)/10000.0f)*360;
        zRotation = ((now % 10000)/10000.0f)*360;
    }


    class Color {
        float r, g, b;
        Color(final float r, final float g, final float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        void apply() { gl.glColor3f(r, g, b); }
    }

    class Vec3 {
        double x, y, z;

        Vec3(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        void translate() { gl.glTranslated(x, y, z); }
        void rotate(final float degrees) { gl.glRotated(degrees, x, y, z); }
        void putVertex() { gl.glVertex3d(x, y, z); }
    }

    private final Color Red = new Color(1.0f, 0.0f, 0.0f);
    private final Color Green = new Color(0.0f, 1.0f, 0.0f);
    private final Color Blue = new Color(0.0f, 0.0f, 1.0f);
    private final Color White = new Color(1.0f, 1.0f, 1.0f);

    private static final float endTriangleRadius = 1.0f; // Distance from center to each point of end-cap triangle
    private static final double[] endTriangleIndices = { 0, Math.toRadians(120), Math.toRadians(240) };

    // End triangles have 3 points equidistant around the unit circle (0, 120, 240 degrees)
    // these values are cos(th), sin(th) for each of the 3 points above, scaled by the radius above
    private static final double[] endTriangleXIndices = {
            endTriangleRadius*Math.cos(endTriangleIndices[0]),
            endTriangleRadius*Math.cos(endTriangleIndices[1]),
            endTriangleRadius*Math.cos(endTriangleIndices[2]) };
    private static final double[] endTriangleYIndices = {
            endTriangleRadius*Math.sin(endTriangleIndices[0]),
            endTriangleRadius*Math.sin(endTriangleIndices[1]),
            endTriangleRadius*Math.sin(endTriangleIndices[2]) };

    // Actually we want 4 equidistance spherical coordinates I suppose... corresponding to which surface points,
    // though?

    // Math I got off of the internet for generating equidistant points on a sphere.  Unfortunately
    // I don't know what order the points are emitted in.
    private Vec3[] buildEquilateralPoly(final int vertexCount, final double radius) {
        final Vec3[] out = new Vec3[vertexCount];

        final double stopIndex=vertexCount+1;
        final double pointArea = 4*Math.PI*radius*radius/stopIndex; // area of patch belonging to each point
        final double pointDiameter = Math.sqrt(pointArea); // 'diameter' of portion of surface belonging to each point

        int i = 0; // index of current point being generated

        final long MTheta = Math.round(Math.PI/pointDiameter); // # patches per point diameter that fit around a circle
        final double diamTheta = Math.PI/MTheta; // Quantized diameter in theta (?)
        final double diamPhi = pointArea/diamTheta; // Quantized diameter in phi (?)

        for (int m=0;m<MTheta;++m) {
            final double theta = Math.PI*(m+0.5f)/MTheta;
            final long Mphi = Math.round(2*Math.PI*Math.sin(theta)/diamPhi);
            for (int n=0;n<Mphi;++n) {
                final double phi = 2*Math.PI*n/Mphi;
                out[i++] = new Vec3(
                    Math.sin(theta)*Math.cos(phi),
                    Math.sin(theta)*Math.sin(phi),
                    Math.cos(theta));
            }
        }

        return out;
    }

    @Override
    public void render(final GLAutoDrawable renderTarget) {
        log.d("render called");

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity(); // reset current transformation matrix

        final Vec3[] trianglePoints = buildEquilateralPoly(4, 1.0f);

        gl.glColor3f(1.0f,1.0f,1.0f);

        gl.glPushMatrix(); {
            gl.glBegin(GL.GL_TRIANGLES); { // no QUADS any more apparently
                for (int i=0;i<3;++i)
                    trianglePoints[i].putVertex();
            } gl.glEnd();
        } gl.glPopMatrix(); // pop transformation matrix back to root
    }

    @Override
    public void dispose(final GLAutoDrawable renderTarget) {
        super.dispose(renderTarget); // TODO: no longer call super after decoupling
    }
}
