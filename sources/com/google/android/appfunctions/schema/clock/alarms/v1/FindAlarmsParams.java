package com.google.android.appfunctions.schema.clock.alarms.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u0000 !2\u00020\u0001:\u0001!BW\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010 \u001a\u00020\u000bH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001e\u0010\f\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001b\u0010\u0015\u001a\u0004\b\u001c\u0010\u0013¨\u0006\""}, d2 = {"Lcom/google/android/appfunctions/schema/clock/alarms/v1/FindAlarmsParams;", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endTime", Todo.LABEL, "", "alarmStatus", "dayPattern", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", "maxCount", "", "sortingOrder", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;ILjava/lang/String;)V", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndTime", "getLabel", "()Ljava/lang/String;", "getAlarmStatus$annotations", "()V", "getAlarmStatus", "getDayPattern", "()Lcom/google/android/appfunctions/schema/clock/alarms/v1/DayPattern;", "getMaxCount", "()I", "getSortingOrder$annotations", "getSortingOrder", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindAlarmsParams {
    public static final String SORTING_ORDER_START_TIME_ASCENDING = "START_TIME_ASCENDING";
    public static final String SORTING_ORDER_START_TIME_DESCENDING = "START_TIME_DESCENDING";
    private final String alarmStatus;
    private final DayPattern dayPattern;
    private final DateTime endTime;
    private final String label;
    private final int maxCount;
    private final String sortingOrder;
    private final DateTime startTime;

    public FindAlarmsParams(DateTime dateTime, DateTime dateTime2, String str, String str2, DayPattern dayPattern, int i, String str3) {
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.label = str;
        this.alarmStatus = str2;
        this.dayPattern = dayPattern;
        this.maxCount = i;
        this.sortingOrder = str3;
    }

    public /* synthetic */ FindAlarmsParams(DateTime dateTime, DateTime dateTime2, String str, String str2, DayPattern dayPattern, int i, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : dateTime, (i2 & 2) != 0 ? null : dateTime2, (i2 & 4) != 0 ? null : str, (i2 & 8) != 0 ? null : str2, (i2 & 16) != 0 ? null : dayPattern, i, (i2 & 64) != 0 ? null : str3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindAlarmsParams)) {
            return false;
        }
        FindAlarmsParams findAlarmsParams = (FindAlarmsParams) other;
        return Intrinsics.areEqual(this.startTime, findAlarmsParams.startTime) && Intrinsics.areEqual(this.endTime, findAlarmsParams.endTime) && Intrinsics.areEqual(this.label, findAlarmsParams.label) && Intrinsics.areEqual(this.alarmStatus, findAlarmsParams.alarmStatus) && Intrinsics.areEqual(this.dayPattern, findAlarmsParams.dayPattern) && this.maxCount == findAlarmsParams.maxCount && Intrinsics.areEqual(this.sortingOrder, findAlarmsParams.sortingOrder);
    }

    public final String getAlarmStatus() {
        return this.alarmStatus;
    }

    public final DayPattern getDayPattern() {
        return this.dayPattern;
    }

    public final DateTime getEndTime() {
        return this.endTime;
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

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime, this.label, this.alarmStatus, this.dayPattern, Integer.valueOf(this.maxCount), this.sortingOrder);
    }
}
