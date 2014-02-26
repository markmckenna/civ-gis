package com.lantopia.civgis.utils;

import jogamp.common.Debug;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 *
 * Instances are not threadsafe (but instance collector is).  It's a good idea to create one Log instance
 * per logging class, or at least one per thread.
 */
@SuppressWarnings("UnusedDeclaration")
public class Log {
    public enum Level {
        Error("ERROR", 0),
        Warn("WARN", 1),
        Info("INFO", 2),
        Debug("DEBUG", 3);

        public final String label;
        public final int rank;

        Level(final String label, final int rank) { this.label = label; this.rank = rank; }

        public String toString() { return label; }
    }

    private static final Object classMutex = new Object();
    private static final Map<String,Log> loggerCache = new TreeMap<String, Log>();
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS");
    private static final DateTimeZone timezone = DateTimeZone.UTC;
    private static DateTime lastMessageTime = DateTime.now(timezone);
    private static Log metalogger = new Log(Log.class.getSimpleName());
    private static Level globalLogLevel = Level.Debug;

    static {
        // TODO: Metalogger time delta should be minimal
        metalogger.msg(System.out, Level.Info, "Initializing logger");
    }

    public static void logLevel(final Level level) {
        synchronized (classMutex) {
            globalLogLevel = level;
        }
    }

    public static void logLevel(final String name, final Level level) {
        final Log log = getInstance(name);
        log.setLogLevel(level);
    }

    public static Log getInstance(final String name) {
        synchronized (classMutex) {
            if (!loggerCache.containsKey(name)) loggerCache.put(name, new Log(name));
            return loggerCache.get(name);
        }
    }


    private final String context;
    private Level logLevel = Level.Info;

    public Log(final String context) {
        this.context = context;
    }

    public Log(final String context, final Level level) {
        this.context = context;
        this.setLogLevel(level);
    }


    public void setLogLevel(final Level level) {
        logLevel = level;
    }

    public void e(final String msg) { msg(System.err, Level.Error, msg); }
    public void e(final Throwable t) { msg(System.err, Level.Error, t); }
    public void w(final String msg) { msg(System.err, Level.Warn, msg); }
    public void w(final Throwable t) { msg(System.err, Level.Warn, t); }
    public void i(final String msg) { msg(System.out, Level.Info, msg); }
    public void i(final Throwable t) { msg(System.out, Level.Info, t); }
    public void d(final String msg) { msg(System.out, Level.Debug, msg); }
    public void d(final Throwable t) { msg(System.out, Level.Debug, t); }

    public void e(final String fmt, final Object... args) { msg(System.err, Level.Error, String.format(fmt, args)); }
    public void w(final String fmt, final Object... args) { msg(System.err, Level.Warn, String.format(fmt, args)); }
    public void i(final String fmt, final Object... args) { msg(System.out, Level.Info, String.format(fmt, args)); }
    public void d(final String fmt, final Object... args) { msg(System.out, Level.Debug, String.format(fmt, args)); }

    /**
     * Use like this:
     *  if (x==null) then return log.e("X was null!", defaultValue);
     */
    public <T> T e(final String msg, final T result) { e(msg); return result; }

    /**
     * Use like this:
     *  if (x==null) then return log.w("X was null!", defaultValue);
     */
    public <T> T w(final String msg, final T result) { w(msg); return result; }

    /**
     * Use like this:
     *  try { ... }
     *  catch (Exception e) { return log.e(e, defaultValue); }
     */
    public <T> T e(final Throwable t, final T result) { e(t); return result; }

    /**
     * Use like this:
     *  try { ... }
     *  catch (Exception e) { return log.w(e, defaultValue); }
     */
    public <T> T w(final Throwable t, final T result) { w(t); return result; }

    private String timestamp() {
        final DateTime now = DateTime.now(timezone);
        final String out = formatter.print(now) + '+' + String.format("%04d", new Interval(lastMessageTime, now).toDurationMillis());
        lastMessageTime = now;
        return out;
    }

    private void msg(final PrintStream stream, final Level level, final Throwable t) {
        if (globalLogLevel.rank > logLevel.rank) return;
        if (level.rank > logLevel.rank) return;
        msg(stream, level, t.getMessage());
        t.printStackTrace(stream);
    }

    private void msg(final PrintStream stream, final Level level, final String msg) {
        if (globalLogLevel.rank > logLevel.rank) return;
        if (level.rank > logLevel.rank) return;
        stream.println(String.format("[%s %s/%s] %s", timestamp(), context, level, msg));
    }

    public static boolean isDebug() { return Debug.debugAll(); }
}
