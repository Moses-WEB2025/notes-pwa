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
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B3\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0006H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\r\u001a\u0004\b\u0012\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/timers/v1/FindTimersParams;", "", Todo.LABEL, "", "timerState", "maxCount", "", "sortingOrder", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getTimerState$annotations", "()V", "getTimerState", "getMaxCount", "()I", "getSortingOrder$annotations", "getSortingOrder", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.timers.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindTimersParams {
    public static final String SORTING_ORDER_EXPIRE_TIME_ASCENDING = "EXPIRE_TIME_ASCENDING";
    public static final String SORTING_ORDER_EXPIRE_TIME_DESCENDING = "EXPIRE_TIME_DESCENDING";
    private final String label;
    private final int maxCount;
    private final String sortingOrder;
    private final String timerState;

    public FindTimersParams(String str, String str2, int i, String str3) {
        this.label = str;
        this.timerState = str2;
        this.maxCount = i;
        this.sortingOrder = str3;
    }

    public /* synthetic */ FindTimersParams(String str, String str2, int i, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? null : str2, i, (i2 & 8) != 0 ? null : str3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindTimersParams)) {
            return false;
        }
        FindTimersParams findTimersParams = (FindTimersParams) other;
        return Intrinsics.areEqual(this.label, findTimersParams.label) && Intrinsics.areEqual(this.timerState, findTimersParams.timerState) && this.maxCount == findTimersParams.maxCount && Intrinsics.areEqual(this.sortingOrder, findTimersParams.sortingOrder);
    }

    public final String getLabel() {
        return this.label;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getSortingOrder() {
        return this.sortingOrder;
    }

    public final String getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.label, this.timerState, Integer.valueOf(this.maxCount), this.sortingOrder);
    }
}
