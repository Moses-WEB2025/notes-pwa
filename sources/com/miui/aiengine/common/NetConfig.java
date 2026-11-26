package com.miui.aiengine.common;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NetConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\fR\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR:\u0010\u001f\u001a\"\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006'"}, d2 = {"Lcom/miui/aiengine/common/NetConfig;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "AI_SID", "SSE", "", "getSSE", "()Z", "setSSE", "(Z)V", "SSEProtocolPrivate", "getSSEProtocolPrivate", "setSSEProtocolPrivate", "logOn", "getLogOn", "setLogOn", "NET_WORK_LOG_LEVEL", "Lcom/miui/aiengine/common/LogFilterLevel;", "getNET_WORK_LOG_LEVEL", "()Lcom/miui/aiengine/common/LogFilterLevel;", "setNET_WORK_LOG_LEVEL", "(Lcom/miui/aiengine/common/LogFilterLevel;)V", "timeOutConfig", "Lcom/miui/aiengine/common/TimeOutConfig;", "getTimeOutConfig", "()Lcom/miui/aiengine/common/TimeOutConfig;", "setTimeOutConfig", "(Lcom/miui/aiengine/common/TimeOutConfig;)V", "logAgent", "Lkotlin/Function4;", "", "", "getLogAgent", "()Lkotlin/jvm/functions/Function4;", "setLogAgent", "(Lkotlin/jvm/functions/Function4;)V", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NetConfig {
    public static final String AI_SID = "hyperos_sec_common";
    private static boolean SSEProtocolPrivate = false;
    public static final String TAG = "libAiCommon";
    private static Function4<? super Integer, ? super String, ? super String, ? super String, Unit> logAgent;
    private static boolean logOn;
    public static final NetConfig INSTANCE = new NetConfig();
    private static boolean SSE = true;
    private static LogFilterLevel NET_WORK_LOG_LEVEL = LogFilterLevel.BASIC;
    private static TimeOutConfig timeOutConfig = new TimeOutConfig(0, 0, 0, 0, 15, null);

    private NetConfig() {
    }

    public final boolean getSSE() {
        return SSE;
    }

    public final void setSSE(boolean z) {
        SSE = z;
    }

    public final boolean getSSEProtocolPrivate() {
        return SSEProtocolPrivate;
    }

    public final void setSSEProtocolPrivate(boolean z) {
        SSEProtocolPrivate = z;
    }

    public final boolean getLogOn() {
        return logOn;
    }

    public final void setLogOn(boolean z) {
        logOn = z;
    }

    public final LogFilterLevel getNET_WORK_LOG_LEVEL() {
        return NET_WORK_LOG_LEVEL;
    }

    public final void setNET_WORK_LOG_LEVEL(LogFilterLevel logFilterLevel) {
        Intrinsics.checkNotNullParameter(logFilterLevel, "<set-?>");
        NET_WORK_LOG_LEVEL = logFilterLevel;
    }

    public final TimeOutConfig getTimeOutConfig() {
        return timeOutConfig;
    }

    public final void setTimeOutConfig(TimeOutConfig timeOutConfig2) {
        Intrinsics.checkNotNullParameter(timeOutConfig2, "<set-?>");
        timeOutConfig = timeOutConfig2;
    }

    public final Function4<Integer, String, String, String, Unit> getLogAgent() {
        return logAgent;
    }

    public final void setLogAgent(Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4) {
        logAgent = function4;
    }
}
