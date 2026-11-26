package com.xiaomi.miai.log;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleLoggerFactory extends LoggerFactory {
    @Override // com.xiaomi.miai.log.LoggerFactory
    public Logger getLogger(String str) {
        return new SimpleLogger(str);
    }

    static class SimpleLogger extends Logger {
        private String loggerName;

        SimpleLogger(String str) {
            this.loggerName = str;
        }

        @Override // com.xiaomi.miai.log.Logger
        public void trace(String str, Object... objArr) {
            log("TRACE", str, objArr);
        }

        @Override // com.xiaomi.miai.log.Logger
        public void debug(String str, Object... objArr) {
            log("DEBUG", str, objArr);
        }

        @Override // com.xiaomi.miai.log.Logger
        public void info(String str, Object... objArr) {
            log("INFO ", str, objArr);
        }

        @Override // com.xiaomi.miai.log.Logger
        public void warn(String str, Object... objArr) {
            log("WARN ", str, objArr);
        }

        @Override // com.xiaomi.miai.log.Logger
        public void error(String str, Object... objArr) {
            log("ERROR", str, objArr);
        }

        private void log(String str, String str2, Object[] objArr) {
            System.out.println("[" + str + "|" + this.loggerName + "] " + MessageFormatter.arrayFormat(str2, objArr));
        }
    }
}
