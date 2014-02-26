package com.lantopia.civgis.renderer;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 *
 * TODO: Consider splitting this into a PausableRenderable wrapper that delegates to a regular Screen
 * and adds pause/resume capability (paused Screen still renders, but doesn't update; and when it is
 * unpaused, it picks up from where it was paused in terms of time--rather than 'jumping' to match the new
 * time index)
 */
public abstract class BaseRenderer<T> implements Renderer<T> {
    private enum State {
        New,
        Stopped,
        Running,
        Paused,
        Transitioning
    }

    private long baseTime =0;
    private long lastPauseTime =0;
    private long lastUpdate =0;

    private final AtomicReference<State> state = new AtomicReference<State>(State.New);

    // TODO: final
    private T renderTarget;

    protected final void setRenderTarget(final T target) {
        state.compareAndSet(State.New, State.Transitioning);
        // TODO: Constructor
        this.renderTarget = target;
        initialize(target);
        state.set(State.Stopped);
    }

    public final void start() {
        state.compareAndSet(State.Stopped, State.Transitioning); // TODO: check
        baseTime = System.currentTimeMillis();
        lastUpdate = 0;
        state.set(State.Running);
        update(baseTime, 0);
    }

    public final void stop() {
        state.set(State.Stopped); // TODO: transition?
    }

    public final void pause() {
        state.compareAndSet(State.Running, State.Transitioning);
        lastPauseTime = System.currentTimeMillis();
        state.set(State.Paused);
    }

    public final void resume() {
        state.compareAndSet(State.Paused, State.Transitioning);
        final long pausedTime = System.currentTimeMillis() - lastPauseTime;
        baseTime += pausedTime;
        state.set(State.Running);
    }

    public final void cycle(final T renderTarget) {
        if (this.renderTarget != renderTarget) throw new RuntimeException("ERROR: render target instance changed!"); // TODO: typed

        final State state = this.state.get();

        if (state == State.Running) {
            final long now = System.currentTimeMillis() - baseTime;
            update(now, now-lastUpdate);
            lastUpdate += now- baseTime;
        }

        if (state != State.Stopped) render(renderTarget);
    }

    public final boolean isRunning() { return (state.get() == State.Running); }
    public final boolean isPaused() { return (state.get() == State.Paused); }

    public abstract void update(final long now, final long millisSinceLastUpdate);
    public abstract void render(final T renderTarget);
}
