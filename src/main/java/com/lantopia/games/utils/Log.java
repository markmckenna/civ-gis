package com.lantopia.games.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.PrintStream;

/**
 * @author Mark McKenna %lt;mark.mckenna@teamspace.ca>
 * @version 0.1
 * @since 11/02/14
 */
@SuppressWarnings("UnusedDeclaration")
public class Log {
    private final String context;

    public Log(final String context) {
        this.context = context;
    }

    public enum Level {
        Error("ERROR"),
        Warn("WARN"),
        Info("INFO"),
        Debug("DEBUG");

        public final String label;

        Level(final String label) { this.label = label; }

        public String toString() { return label; }
    }

    public void e(final String msg) { msg(System.err, Level.Error, msg); }
    public void e(final Throwable t) { msg(System.err, Level.Error, t); }
    public void w(final String msg) { msg(System.err, Level.Warn, msg); }
    public void w(final Throwable t) { msg(System.err, Level.Warn, t); }
    public void i(final String msg) { msg(System.out, Level.Info, msg); }
    public void i(final Throwable t) { msg(System.out, Level.Info, t); }
    public void d(final String msg) { msg(System.out, Level.Debug, msg); }
    public void d(final Throwable t) { msg(System.out, Level.Debug, t); }

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

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS");
    private static final DateTimeZone timezone = DateTimeZone.UTC;
    private static DateTime lastMessageTime = DateTime.now(timezone);

    private static Log metalogger = new Log(Log.class.getSimpleName());

    static {
        metalogger.msg(System.out, Level.Info, "Initializing logger");
    }

    private String timestamp() {
        final DateTime now = DateTime.now(timezone);
        final String out = formatter.print(now) + '+' + String.format("%04d", new Interval(lastMessageTime, now).toDurationMillis());
        lastMessageTime = now;
        return out;
    }

    private void msg(final PrintStream stream, final Level level, final Throwable t) {
        msg(stream, level, t.getMessage());
        t.printStackTrace(stream);
    }

    private void msg(final PrintStream stream, final Level level, final String msg) {
        stream.println(String.format("[%s %s/%s] %s", timestamp(), context, level, msg));
    }
}
