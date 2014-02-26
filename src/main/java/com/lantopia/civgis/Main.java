package com.lantopia.civgis;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;
import com.lantopia.civgis.gl.AbstractGLEventListener;
import com.lantopia.civgis.utils.Log;

import javax.media.opengl.*;

public class Main {
    private static Log log = new Log(Main.class.getSimpleName());

    static {
        Log.logLevel(Log.Level.Info);
    }

    public static void main(final String[] args) {
        log.i("Starting GL demo app...");

        log.i("Initializing GLProfile singleton...");
        GLProfile.initSingleton();

        log.i("Checking if GL2 is available...");
        if (!GLProfile.isAvailable(GLProfile.GL2))
            throw new RuntimeException("GL2 not available");

        log.i("Creating GL profile");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);

        log.i("Preparing GL capabilities");
        final GLCapabilities capabilities = new GLCapabilities(profile);
        log.i("Capabilities: " + capabilities);

        log.i("Creating NEWT display");
        final Display display = NewtFactory.createDisplay(null);

        log.i("Creating NEWT screen");
        final Screen screen = NewtFactory.createScreen(display, 0);

        log.i("Creating GL window");
        final GLWindow window = GLWindow.create(screen, capabilities);

        log.i("Starting fixed-frame-rate animation loop");
        final GLAnimatorControl animator = new FPSAnimator(window, 30);

        log.i("Hooking destructor event");
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(final WindowEvent e) {
                log.i("Terminating GL demo app...");
                animator.stop();
            }
        });

        final Demo demo = new Demo();
        log.i("Binding new game app");
        window.addGLEventListener(demo);

        log.i("Starting animation");
        animator.start();

        window.addGLEventListener(new AbstractGLEventListener() {
            @Override
            public void init(final GLAutoDrawable drawable) {
                demo.start();
            }
        });

        log.i("Visibilizing window");
        window.setTitle("Hello world");
        window.setPosition(30,30);
        window.setSize(640, 640);
        window.setFullscreen(false);
        window.setVisible(true);
        window.setPointerVisible(false);
    }
}
