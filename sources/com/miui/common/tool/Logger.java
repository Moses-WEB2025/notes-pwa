package com.miui.common.tool;

import android.app.Application;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Logger.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f¨\u0006\u0012"}, d2 = {"Lcom/miui/common/tool/Logger;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "init", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/app/Application;", "miuixLogEnable", "", "v", "tag", "", "msg", DateTokenConverter.CONVERTER_KEY, IntegerTokenConverter.CONVERTER_KEY, AnimatedProperty.PROPERTY_NAME_W, "e", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Logger {
    public static final Logger INSTANCE = new Logger();

    public final void init(Application context, boolean miuixLogEnable) {
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private Logger() {
    }

    public final void v(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        com.miui.notes.base.utils.Logger.INSTANCE.v(tag, msg);
    }

    public final void d(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        com.miui.notes.base.utils.Logger.INSTANCE.d(tag, msg);
    }

    public final void i(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        com.miui.notes.base.utils.Logger.INSTANCE.i(tag, msg);
    }

    public final void w(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        com.miui.notes.base.utils.Logger.INSTANCE.w(tag, msg);
    }

    public final void e(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        com.miui.notes.base.utils.Logger.INSTANCE.e(tag, msg);
    }
}
