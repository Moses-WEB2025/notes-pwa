package com.miui.creation.logger.inner;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.selector.DefaultContextSelector;

/* JADX INFO: loaded from: classes2.dex */
public class ContextSelectorHijacker extends DefaultContextSelector {
    public ContextSelectorHijacker(LoggerContext loggerContext) {
        super(loggerContext);
        initLogger(loggerContext);
    }

    private void initLogger(LoggerContext loggerContext) {
        XCLoggerFactory.instance().doInit(loggerContext);
    }
}
