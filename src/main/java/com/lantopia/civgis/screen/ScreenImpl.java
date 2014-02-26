package com.lantopia.civgis.screen;

import com.lantopia.civgis.renderer.Renderer;
import com.lantopia.civgis.input.InputHandler;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 */
public class ScreenImpl {
    private final Renderer renderer;
    private final InputHandler handler;

    ScreenImpl(final Renderer renderer, final InputHandler handler) {
        this.renderer = renderer;
        this.handler = handler;
    }
}
