package com.miui.creation.logger.inner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes2.dex */
class LogcatTurboFilter extends TurboFilter {
    LogcatTurboFilter() {
    }

    @Override // ch.qos.logback.classic.turbo.TurboFilter
    public FilterReply decide(Marker marker, Logger logger, Level level, String str, Object[] objArr, Throwable th) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }
        if (marker != null && marker.contains(Markers.getFileMarker())) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.NEUTRAL;
    }
}
