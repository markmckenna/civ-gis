package com.lantopia.civgis.screen;

import com.lantopia.civgis.utils.Disposable;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 */
public interface Screen extends Disposable {
    void initialize();
    void start();
    void stop();
    void dispose();
}
