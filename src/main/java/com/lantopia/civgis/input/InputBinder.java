package com.lantopia.civgis.input;

import com.lantopia.civgis.utils.Disposable;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 */
public interface InputBinder {
    public interface Binding extends Disposable {}

    public interface InputAction {
        void execute(KeyPattern pattern);
    }

    Binding bind(final KeyPattern pattern, final InputAction action);
}
