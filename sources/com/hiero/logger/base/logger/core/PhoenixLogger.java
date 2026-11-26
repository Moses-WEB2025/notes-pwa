package com.hiero.logger.base.logger.core;

import android.util.Log;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.hiero.logger.base.logger.Constants;
import com.hiero.logger.base.logger.interfaces.ILogger;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u0003\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005JY\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\u0005H\u0086 J\t\u0010\u0013\u001a\u00020\u0007H\u0086 J\t\u0010\u0014\u001a\u00020\u0007H\u0086 J%\u0010\u0015\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\tH\u0096 J%\u0010\u0019\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\tH\u0096 J%\u0010\u001a\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\tH\u0096 J%\u0010\u001b\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\tH\u0096 J%\u0010\u001c\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\tH\u0096 J$\u0010\u001d\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016¨\u0006 "}, d2 = {"Lcom/hiero/logger/base/logger/core/PhoenixLogger;", "Lcom/hiero/logger/base/logger/interfaces/ILogger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "loadLib", "", "init", "", "path", "", "cacheDir", "logDir", "maxFileSize", "", "debugInfo", "consolePrint", "compress", "headerInfo", "fastMode", "uninit", "flush", "e", "tag", CallMethod.ARG_EXTRA_STRING, "msg", AnimatedProperty.PROPERTY_NAME_W, DateTokenConverter.CONVERTER_KEY, IntegerTokenConverter.CONVERTER_KEY, "v", "trace", "tr", "", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PhoenixLogger implements ILogger {
    public static final PhoenixLogger INSTANCE;

    static {
        PhoenixLogger phoenixLogger = new PhoenixLogger();
        INSTANCE = phoenixLogger;
        phoenixLogger.loadLib();
    }

    private PhoenixLogger() {
    }

    public static /* synthetic */ void init$default(PhoenixLogger phoenixLogger, String str, String str2, String str3, int i, boolean z, boolean z2, boolean z3, String str4, boolean z4, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            z = false;
        }
        if ((i2 & 32) != 0) {
            z2 = true;
        }
        if ((i2 & 64) != 0) {
            z3 = true;
        }
        if ((i2 & 256) != 0) {
            z4 = false;
        }
        phoenixLogger.init(str, str2, str3, i, z, z2, z3, str4, z4);
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public native void d(String tag, String extra, String msg);

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public native void e(String tag, String extra, String msg);

    public final native void flush();

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public native void i(String tag, String extra, String msg);

    public final native void init(String path, String cacheDir, String logDir, int maxFileSize, boolean debugInfo, boolean consolePrint, boolean compress, String headerInfo, boolean fastMode);

    public final boolean loadLib() {
        try {
            System.loadLibrary("phoenix");
            Log.i(Constants.MODULE_TAG, "loadLib finished");
            return true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(Constants.MODULE_TAG, "should load lib first!!!");
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public void trace(String tag, String extra, Throwable tr) {
        Intrinsics.checkNotNullParameter(tr, "tr");
    }

    public final native void uninit();

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public native void v(String tag, String extra, String msg);

    @Override // com.hiero.logger.base.logger.interfaces.ILogger
    public native void w(String tag, String extra, String msg);
}
