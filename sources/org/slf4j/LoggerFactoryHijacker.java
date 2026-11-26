package org.slf4j;

import org.slf4j.impl.StaticLoggerBinder;

/* JADX INFO: loaded from: classes6.dex */
public class LoggerFactoryHijacker {
    public static boolean isInitializeStarted() {
        return LoggerFactory.INITIALIZATION_STATE != 0;
    }

    public static ILoggerFactory getLoggerFactory() {
        int i = LoggerFactory.INITIALIZATION_STATE;
        if (i != 0 && i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return StaticLoggerBinder.getSingleton().getLoggerFactory();
                }
                if (i != 4) {
                    throw new IllegalStateException("Unreachable code");
                }
            }
            return LoggerFactory.NOP_FALLBACK_FACTORY;
        }
        return LoggerFactory.SUBST_FACTORY;
    }
}
