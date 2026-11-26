package com.hiero.logger.base.logger.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0086 ¨\u0006\t"}, d2 = {"Lcom/hiero/logger/base/logger/utils/FstIO;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "copyFile", "", "sourcePath", "", "destPath", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FstIO {
    public static final FstIO INSTANCE = new FstIO();

    private FstIO() {
    }

    public final native int copyFile(String sourcePath, String destPath);
}
