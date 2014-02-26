package com.lantopia.civgis.renderer;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
@SuppressWarnings("UnusedDeclaration")
public interface Renderer<T> {
    void start();
    void stop();
    void pause();
    void resume();

    boolean isOpaque();
    boolean isRunning();
    boolean isPaused();

    void initialize(final T target);
    void update(long now, long millisSinceLastUpdate);
    void render(final T target);
    void dispose(final T target);
}
