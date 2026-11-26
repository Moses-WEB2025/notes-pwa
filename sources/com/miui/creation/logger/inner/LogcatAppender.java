package com.miui.creation.logger.inner;

import android.util.Log;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

/* JADX INFO: loaded from: classes2.dex */
class LogcatAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private static final int MAX_TAG_LENGTH = 23;
    private LayoutWrappingEncoder<ILoggingEvent> encoder = null;
    private LayoutWrappingEncoder<ILoggingEvent> tagEncoder = null;
    private boolean checkLoggable = false;

    @Override // ch.qos.logback.core.UnsynchronizedAppenderBase, ch.qos.logback.core.spi.LifeCycle
    public void start() {
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = this.encoder;
        if (layoutWrappingEncoder == null || layoutWrappingEncoder.getLayout() == null) {
            addError("No layout set for the appender named [" + this.name + "].");
            return;
        }
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder2 = this.tagEncoder;
        if (layoutWrappingEncoder2 != null) {
            Layout<ILoggingEvent> layout = layoutWrappingEncoder2.getLayout();
            if (layout == null) {
                addError("No tag layout set for the appender named [" + this.name + "].");
                return;
            }
            LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder3 = this.tagEncoder;
            if (layoutWrappingEncoder3 instanceof PatternLayoutEncoder) {
                String pattern = ((PatternLayoutEncoder) layoutWrappingEncoder3).getPattern();
                if (!pattern.contains("%nopex")) {
                    this.tagEncoder.stop();
                    ((PatternLayoutEncoder) this.tagEncoder).setPattern(pattern + "%nopex");
                    this.tagEncoder.start();
                }
                ((PatternLayout) layout).setPostCompileProcessor(null);
            }
        }
        super.start();
    }

    @Override // ch.qos.logback.core.UnsynchronizedAppenderBase
    public void append(ILoggingEvent iLoggingEvent) {
        if (isStarted()) {
            String tag = getTag(iLoggingEvent);
            int i = iLoggingEvent.getLevel().levelInt;
            if (i == Integer.MIN_VALUE || i == 5000) {
                if (!this.checkLoggable || Log.isLoggable(tag, 2)) {
                    Log.v(tag, this.encoder.getLayout().doLayout(iLoggingEvent));
                    return;
                }
                return;
            }
            if (i == 10000) {
                if (!this.checkLoggable || Log.isLoggable(tag, 3)) {
                    Log.d(tag, this.encoder.getLayout().doLayout(iLoggingEvent));
                    return;
                }
                return;
            }
            if (i == 20000) {
                if (!this.checkLoggable || Log.isLoggable(tag, 4)) {
                    Log.i(tag, this.encoder.getLayout().doLayout(iLoggingEvent));
                    return;
                }
                return;
            }
            if (i == 30000) {
                if (!this.checkLoggable || Log.isLoggable(tag, 5)) {
                    Log.w(tag, this.encoder.getLayout().doLayout(iLoggingEvent));
                    return;
                }
                return;
            }
            if (i != 40000) {
                return;
            }
            if (!this.checkLoggable || Log.isLoggable(tag, 6)) {
                Log.e(tag, this.encoder.getLayout().doLayout(iLoggingEvent));
            }
        }
    }

    public LayoutWrappingEncoder<ILoggingEvent> getEncoder() {
        return this.encoder;
    }

    public void setEncoder(LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder) {
        this.encoder = layoutWrappingEncoder;
    }

    public LayoutWrappingEncoder<ILoggingEvent> getTagEncoder() {
        return this.tagEncoder;
    }

    public void setTagEncoder(LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder) {
        this.tagEncoder = layoutWrappingEncoder;
    }

    public void setCheckLoggable(boolean z) {
        this.checkLoggable = z;
    }

    public boolean getCheckLoggable() {
        return this.checkLoggable;
    }

    protected String getTag(ILoggingEvent iLoggingEvent) {
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = this.tagEncoder;
        String strDoLayout = layoutWrappingEncoder != null ? layoutWrappingEncoder.getLayout().doLayout(iLoggingEvent) : iLoggingEvent.getLoggerName();
        return (!this.checkLoggable || strDoLayout.length() <= 23) ? strDoLayout : strDoLayout.substring(0, 22) + "*";
    }
}
