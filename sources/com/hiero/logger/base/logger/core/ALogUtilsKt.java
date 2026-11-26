package com.hiero.logger.base.logger.core;

import java.text.SimpleDateFormat;
import java.util.Locale;
import kotlin.Metadata;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"format", "Ljava/text/SimpleDateFormat;", "getFormat", "()Ljava/text/SimpleDateFormat;", "logger_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class ALogUtilsKt {
    private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss SSS", Locale.CHINA);

    public static final SimpleDateFormat getFormat() {
        return format;
    }
}
