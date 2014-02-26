package com.lantopia.civgis.input;

import com.lantopia.civgis.utils.Disposable;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 */
public interface InputHandler {
    boolean handle(final KeyPattern pattern);
}
