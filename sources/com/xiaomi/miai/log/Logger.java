package com.xiaomi.miai.log;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Logger {
    private static LoggerFactory loggerFactory;

    public abstract void debug(String str, Object... objArr);

    public abstract void error(String str, Object... objArr);

    public abstract void info(String str, Object... objArr);

    public abstract void trace(String str, Object... objArr);

    public abstract void warn(String str, Object... objArr);

    static {
        try {
            loggerFactory = (LoggerFactory) Thread.currentThread().getContextClassLoader().loadClass("com.xiaomi.miai.log.LoggerFactoryImpl").getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ReflectiveOperationException e) {
            System.err.println("Creating LoggerFactory failed, use default one.");
            e.printStackTrace();
            loggerFactory = new SimpleLoggerFactory();
        }
    }

    public static Logger getLogger(String str) {
        return loggerFactory.getLogger(str);
    }
}
