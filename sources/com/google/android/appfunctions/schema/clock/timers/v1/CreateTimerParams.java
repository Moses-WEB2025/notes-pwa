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
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B?\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\f¨\u0006\u0019"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/timers/v1/CreateTimerParams;", "", Todo.LABEL, "", "originalDurationMillis", "", "timerState", "externalId", "id", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getOriginalDurationMillis", "()J", "getTimerState$annotations", "()V", "getTimerState", "getExternalId", "getId", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.timers.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreateTimerParams {
    private final String externalId;
    private final String id;
    private final String label;
    private final long originalDurationMillis;
    private final String timerState;

    public CreateTimerParams(String str, long j, String str2, String str3, String str4) {
        this.label = str;
        this.originalDurationMillis = j;
        this.timerState = str2;
        this.externalId = str3;
        this.id = str4;
    }

    public /* synthetic */ CreateTimerParams(String str, long j, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : str, j, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreateTimerParams)) {
            return false;
        }
        CreateTimerParams createTimerParams = (CreateTimerParams) other;
        return Intrinsics.areEqual(this.label, createTimerParams.label) && this.originalDurationMillis == createTimerParams.originalDurationMillis && Intrinsics.areEqual(this.timerState, createTimerParams.timerState) && Intrinsics.areEqual(this.externalId, createTimerParams.externalId) && Intrinsics.areEqual(this.id, createTimerParams.id);
    }

    public final String getExternalId() {
        return this.externalId;
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

    public final String getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.label, Long.valueOf(this.originalDurationMillis), this.timerState, this.externalId, this.id);
    }
}
