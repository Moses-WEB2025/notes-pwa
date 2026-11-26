package com.miui.creation.logger;

import ch.qos.logback.classic.Level;
import miuix.core.util.RomUtils;
import miuix.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class LoggerConfig {
    private static final boolean IS_DEBUGGABLE;
    private static final boolean IS_MIUI;
    private String mFileLogPath;
    private Level mLogcatLevel;

    static {
        boolean zIsMiuiRom = RomUtils.isMiuiRom();
        IS_MIUI = zIsMiuiRom;
        IS_DEBUGGABLE = zIsMiuiRom && Build.IS_DEBUGGABLE;
    }

    private static final class SingletonHolder {
        private static final LoggerConfig INSTANCE = new LoggerConfig();

        private SingletonHolder() {
        }
    }

    public static LoggerConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private LoggerConfig() {
        this.mLogcatLevel = Level.WARN;
    }

    public void setLogcatLevel(int i) {
        if (i == 2) {
            this.mLogcatLevel = Level.TRACE;
            return;
        }
        if (i == 3) {
            this.mLogcatLevel = Level.DEBUG;
            return;
        }
        if (i == 4) {
            this.mLogcatLevel = Level.INFO;
            return;
        }
        if (i == 5) {
            this.mLogcatLevel = Level.WARN;
        } else if (i == 6) {
            this.mLogcatLevel = Level.ERROR;
        } else {
            this.mLogcatLevel = Level.OFF;
        }
    }

    public Level getLogcatLevel() {
        return Level.OFF;
    }

    public void setFileLogPath(String str) {
        this.mFileLogPath = str;
    }

    public String getFileLogPath() {
        return this.mFileLogPath;
    }
}
