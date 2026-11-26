package ch.qos.logback.classic.spi;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.spi.ContextAware;

/* JADX INFO: loaded from: classes.dex */
public interface LoggerContextAware extends ContextAware {
    void setLoggerContext(LoggerContext loggerContext);
}
