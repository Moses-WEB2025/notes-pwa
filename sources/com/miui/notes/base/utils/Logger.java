package com.miui.notes.base.utils;

import android.os.Build;
import android.os.Process;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Logger.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R:\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/base/utils/Logger;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "logAgent", "Lkotlin/Function4;", "", "", "getLogAgent", "()Lkotlin/jvm/functions/Function4;", "setLogAgent", "(Lkotlin/jvm/functions/Function4;)V", "processName", "getProcessName", "()Ljava/lang/String;", "v", "tag", "msg", DateTokenConverter.CONVERTER_KEY, IntegerTokenConverter.CONVERTER_KEY, AnimatedProperty.PROPERTY_NAME_W, "e", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Logger {
    public static final Logger INSTANCE = new Logger();
    public static final String TAG = "NoteApp";
    private static Function4<? super Integer, ? super String, ? super String, ? super String, Unit> logAgent;
    private static final String processName;

    private Logger() {
    }

    public final Function4<Integer, String, String, String, Unit> getLogAgent() {
        return logAgent;
    }

    public final void setLogAgent(Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4) {
        logAgent = function4;
    }

    static {
        String str;
        if (Build.VERSION.SDK_INT >= 33) {
            str = Process.myProcessName() + "|";
        } else {
            str = "";
        }
        processName = str;
    }

    public final String getProcessName() {
        return processName;
    }

    public final void v(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4 = logAgent;
        if (function4 != null) {
            function4.invoke(2, "NoteApp", processName + tag, msg);
        }
    }

    public final void d(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4 = logAgent;
        if (function4 != null) {
            function4.invoke(3, "NoteApp", processName + tag, msg);
        }
    }

    public final void i(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4 = logAgent;
        if (function4 != null) {
            function4.invoke(4, "NoteApp", processName + tag, msg);
        }
    }

    public final void w(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4 = logAgent;
        if (function4 != null) {
            function4.invoke(5, "NoteApp", processName + tag, msg);
        }
    }

    public final void e(String tag, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4 = logAgent;
        if (function4 != null) {
            function4.invoke(6, "NoteApp", processName + tag, msg);
        }
    }
}
