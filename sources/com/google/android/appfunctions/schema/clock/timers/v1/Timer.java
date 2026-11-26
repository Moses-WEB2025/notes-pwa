package com.google.android.appfunctions.schema.clock.timers.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \"2\u00020\u0001:\u0001\"BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u001c\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u000b\u0010\u001c¨\u0006#"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/timers/v1/Timer;", "", "id", "", Todo.LABEL, "originalDurationMillis", "", "durationMillis", "timerState", "remainingDurationMillis", "expireTimeEpochMillis", "isFiring", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;JLjava/lang/Long;Ljava/lang/Boolean;)V", "getId", "()Ljava/lang/String;", "getLabel", "getOriginalDurationMillis", "()J", "getDurationMillis", "getTimerState$annotations", "()V", "getTimerState", "getRemainingDurationMillis", "getExpireTimeEpochMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.timers.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Timer {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.clock.timers.v1.TimerForeground";
    public static final String STATE_EXPIRED = "EXPIRED";
    public static final String STATE_MISSED = "MISSED";
    public static final String STATE_PAUSED = "PAUSED";
    public static final String STATE_RESET = "RESET";
    public static final String STATE_STARTED = "STARTED";
    public static final String STATE_UNKNOWN = "UNKNOWN";
    private final long durationMillis;
    private final Long expireTimeEpochMillis;
    private final String id;
    private final Boolean isFiring;
    private final String label;
    private final long originalDurationMillis;
    private final long remainingDurationMillis;
    private final String timerState;

    public Timer(String id, String str, long j, long j2, String timerState, long j3, Long l, Boolean bool) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(timerState, "timerState");
        this.id = id;
        this.label = str;
        this.originalDurationMillis = j;
        this.durationMillis = j2;
        this.timerState = timerState;
        this.remainingDurationMillis = j3;
        this.expireTimeEpochMillis = l;
        this.isFiring = bool;
    }

    public /* synthetic */ Timer(String str, String str2, long j, long j2, String str3, long j3, Long l, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, j, j2, str3, j3, (i & 64) != 0 ? null : l, (i & 128) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Timer)) {
            return false;
        }
        Timer timer = (Timer) other;
        return Intrinsics.areEqual(this.id, timer.id) && Intrinsics.areEqual(this.label, timer.label) && this.originalDurationMillis == timer.originalDurationMillis && this.durationMillis == timer.durationMillis && Intrinsics.areEqual(this.timerState, timer.timerState) && this.remainingDurationMillis == timer.remainingDurationMillis && Intrinsics.areEqual(this.expireTimeEpochMillis, timer.expireTimeEpochMillis) && Intrinsics.areEqual(this.isFiring, timer.isFiring);
    }

    public final long getDurationMillis() {
        return this.durationMillis;
    }

    public final Long getExpireTimeEpochMillis() {
        return this.expireTimeEpochMillis;
    }

    public final String getId() {
        return this.id;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getOriginalDurationMillis() {
        return this.originalDurationMillis;
    }

    public final long getRemainingDurationMillis() {
        return this.remainingDurationMillis;
    }

    public final String getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.label, Long.valueOf(this.originalDurationMillis), Long.valueOf(this.durationMillis), this.timerState, Long.valueOf(this.remainingDurationMillis), this.expireTimeEpochMillis, this.isFiring);
    }

    /* JADX INFO: renamed from: isFiring, reason: from getter */
    public final Boolean getIsFiring() {
        return this.isFiring;
    }
}
