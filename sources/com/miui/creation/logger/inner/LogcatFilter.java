package com.miui.creation.logger.inner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes2.dex */
class LogcatFilter extends Filter<ILoggingEvent> {
    private volatile Level sLevel = Level.OFF;

    LogcatFilter() {
    }

    public void setLevel(Level level) {
        this.sLevel = level;
    }

    @Override // ch.qos.logback.core.filter.Filter
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        Marker marker = iLoggingEvent.getMarker();
        if (marker != null && marker.contains(Markers.getFileOnlyMarker())) {
            return FilterReply.DENY;
        }
        if (iLoggingEvent.getLevel().isGreaterOrEqual(this.sLevel)) {
            return FilterReply.NEUTRAL;
        }
        return FilterReply.DENY;
    }
}
