package com.lantopia.games;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.lantopia.games.utils.Log;

import javax.media.opengl.*;

public class Main {
    private static Log log = new Log(Main.class.getSimpleName());

    public static void main(final String[] args) {
        log.i("Starting GL demo app...");

        log.i("Initializing GLProfile singleton...");
        GLProfile.initSingleton();

        log.i("Checking if GL3 is available...");
        if (!GLProfile.isAvailable(GLProfile.GL3))
            throw new RuntimeException("GL3 not available");

        log.i("Creating GL profile");
        final GLProfile profile = GLProfile.get(GLProfile.GL3);

        log.i("Preparing GL capabilities");
        final GLCapabilities capabilities = new GLCapabilities(profile);

        log.i("Creating NEWT display");
        final Display display = NewtFactory.createDisplay(null);

        log.i("Creating NEWT screen");
        final Screen screen = NewtFactory.createScreen(display, 0);

        log.i("Creating GL window");
        final GLWindow window = GLWindow.create(screen, capabilities);

        log.i("Visibilizing window");
        window.setTitle("Hello world");
        window.setSize(640, 480);
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(final WindowEvent e) {
                log.i("Terminating GL demo app...");
                System.exit(0);
            }
        });

        window.addGLEventListener(new Demo());

        final GLAnimatorControl animator = new FPSAnimator(window, 30);
        animator.start();
    }
}
