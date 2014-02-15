package com.lantopia.civgis.gl;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
public interface Renderable<T> {
    void start();
    void stop();
    void pause();
    void resume();

    boolean isRunning();
    boolean isPaused();

    void update(long now, long millisSinceLastUpdate);
    void render(T target);
}
