package com.miui.creation.logger.inner;

import ch.qos.logback.classic.pattern.Abbreviator;
import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

/* JADX INFO: loaded from: classes2.dex */
class LogcatTagLayout extends LayoutBase<ILoggingEvent> {
    private final Abbreviator abbreviator = new ClassNameOnlyAbbreviator();

    @Override // ch.qos.logback.core.Layout
    public String doLayout(ILoggingEvent iLoggingEvent) {
        if (!isStarted()) {
            return "";
        }
        return "XCreation_" + this.abbreviator.abbreviate(iLoggingEvent.getLoggerName());
    }
}
