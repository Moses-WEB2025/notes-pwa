package com.google.common.util.concurrent;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public final class UncaughtExceptionHandlers {
    private UncaughtExceptionHandlers() {
    }

    public static Thread.UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }

    static final class Exiter implements Thread.UncaughtExceptionHandler {
        private static final Logger logger = Logger.getLogger(Exiter.class.getName());
        private final Runtime runtime;

        Exiter(Runtime runtime) {
            this.runtime = runtime;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread t, Throwable e) {
            try {
                try {
                    logger.log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", t), e);
                } catch (Error | RuntimeException e2) {
                    System.err.println(e.getMessage());
                    System.err.println(e2.getMessage());
                }
            } finally {
                this.runtime.exit(1);
            }
        }
    }
}
