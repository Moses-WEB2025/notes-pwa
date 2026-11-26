package com.google.android.appfunctions.schema.clock.timers.v1;

import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR$\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u0018"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/timers/v1/UpdateTimerParams;", "", "timerId", "", Todo.LABEL, "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "durationMs", "", "timerState", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getTimerId", "()Ljava/lang/String;", "getLabel", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getDurationMs", "getTimerState$annotations", "()V", "getTimerState", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.timers.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateTimerParams {
    private final SetField<Long> durationMs;
    private final SetField<String> label;
    private final String timerId;
    private final SetField<String> timerState;

    public UpdateTimerParams(String timerId, SetField<String> setField, SetField<Long> setField2, SetField<String> setField3) {
        Intrinsics.checkNotNullParameter(timerId, "timerId");
        this.timerId = timerId;
        this.label = setField;
        this.durationMs = setField2;
        this.timerState = setField3;
    }

    public /* synthetic */ UpdateTimerParams(String str, SetField setField, SetField setField2, SetField setField3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateTimerParams)) {
            return false;
        }
        UpdateTimerParams updateTimerParams = (UpdateTimerParams) other;
        return Intrinsics.areEqual(this.timerId, updateTimerParams.timerId) && Intrinsics.areEqual(this.label, updateTimerParams.label) && Intrinsics.areEqual(this.durationMs, updateTimerParams.durationMs) && Intrinsics.areEqual(this.timerState, updateTimerParams.timerState);
    }

    public final SetField<Long> getDurationMs() {
        return this.durationMs;
    }

    public final SetField<String> getLabel() {
        return this.label;
    }

    public final String getTimerId() {
        return this.timerId;
    }

    public final SetField<String> getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.timerId, this.label, this.durationMs, this.timerState);
    }
}
